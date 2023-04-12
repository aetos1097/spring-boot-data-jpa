package com.estudio.springbootdatajpa.controller;

import com.estudio.springbootdatajpa.controller.util.paginator.PageRender;
import com.estudio.springbootdatajpa.models.dao.IClienteDao;
import com.estudio.springbootdatajpa.models.entity.Cliente;
import com.estudio.springbootdatajpa.models.service.IClienteService;
import com.estudio.springbootdatajpa.models.service.IUploadFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Clase donde iran los controladores
 */
@Secured("ROLE_ADMIN")
@Controller
@SessionAttributes("cliente")//indicamos que se va a guardar en los atributos de la seccion del objeto cliente
public class ClienteController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;

    //Metodo para ver el detalle a travez del id
    @Secured("ROLE_USER")
    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);//clienteService.findOne(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", cliente);
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }

    // ya no inyectamos el Dao si no la fachada IClienteService private IClienteDao clienteDao;
    //listar los elementos
    @Secured("ROLE_USER")
    @RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)//lo mismo que el get
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
                         Authentication authentication,
                         HttpServletRequest request) {

        //validacion de autenticacion
        if (authentication != null) {
            logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));

        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (hasRole("ROLE_ADMIN")) {
            logger.info("Hola usuario ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            logger.info("Hola usuario ".concat(auth.getName()).concat(" No tienes acceso"));
        }
        SecurityContextHolderAwareRequestWrapper securityContext= new SecurityContextHolderAwareRequestWrapper(request,"ROLE_");
        if(securityContext.isUserInRole("ADMIN")){
            logger.info("Forma:SecurityContextHolderAwareRequestWrapper Hola usuario ".concat(auth.getName()).concat(" tienes acceso"));
        }else{
            logger.info("Forma:SecurityContextHolderAwareRequestWrapper Hola usuario ".concat(auth.getName()).concat(" No tienes acceso"));

        }

        /*se coloca como paramtro de entrada @RequestParam(name="page", defaultValue="0")int page
        para la paginacion al momento de listar*/
        Pageable pageRequest = PageRequest.of(page, 5);/*se importa la interfaz y con el metodo  PageRequest.of(page,5)
        Damos el parametro de entrada page y cuantos elementos queremos mostrar(0)
        */
        Page<Cliente> clientes = clienteService.findAll(pageRequest);//realizamos el servicio de filtrado
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        // ya no se usa esta linea porque se quiere traer pero paginar los clientes model.addAttribute("clientes",clienteService.findAll());
        return "listar";
    }

    //ver la imagen de forma preprogamatica atravez de la respuesta de un resource
    @Secured("ROLE_USER")
    @GetMapping("/uploads/{filename:.+}")//el filename.+ ayuda a que la extencion no se trunque con un .png,.jpg, etc..
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {// El Resource se toma del paquete .core.io
        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")/*El valor de este encabezado indica que el contenido de la respuesta se debe
                tratar como un archivo adjunto (attachment) y especifica el nombre del archivo (filename). El nombre del archivo se toma del objeto recurso utilizando el método getFilename().*/
                .body(recurso);//se pasa a al respuesta atravez del responseEntity y se anexa el recurso al cuerpo de la respuesta
        /*.body(recurso), establece el cuerpo de la respuesta como el objeto recurso, que en este caso es un archivo. El objeto recurso se pasa al cuerpo de la respuesta utilizando el método body() de ResponseEntity.*/
    }

    //crear elementos
    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();//creo una nueva instancia

        model.put("cliente", cliente);
        model.put("titulo", "Formulario de cliente");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    //agregar o editar un elemento
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cliente cliente = null;
        //validamos que el id sea mayor 0
        if (id > 0) {
            cliente = clienteService.findOne(id);//me encuentra el valor del id que se pasa en la request
            if (cliente == null) {
                flash.addFlashAttribute("error", "El id del cliente no existe en la base de datos");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El id del cliente no puede ser 0");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar cliente");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    //guardamos los elementos
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {//colocamos la anotacion Valid en el argumento porque sera lo que se envia y debe estar validado
        /*Siempre van juntos El objeto con @Valid y el BindingResult y de resto lo demas
         El BindinfResult es una interfaz que captura los errores en las validaciones
        Ahora validamos con un if si hay algun error en los campos del formulario*/
        if (result.hasErrors()) {
            /*si el objeto se llama igual que en el formulario con metodo get entonces se pasa automatico
            //si no se debe poner en los argumentos :(@Valid @ModelAttribute Cliente cliente, BindingResult result,Model model)
            @RequestParam("file")MultipartFile foto->parametro para inyectar una imagen al html*/
            model.addAttribute("titulo", "Formulario Cliente");
            return "form";//si hay errores nos devolvemos al formulario con ruta /form
        }
        //Agregar una Foto
        if (!foto.isEmpty()) {//verificamos si hay alguna foto para actualziarla
            if (cliente.getId() != null
                    && cliente.getId() > 0
                    && cliente.getFoto() != null
                    && cliente.getFoto().length() > 0) {
                // validamos que la foto exista remplazamos la foto antigua por la nueva
                uploadFileService.delete(cliente.getFoto());
            }
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Path directorioRecursos es para indicar donde se guardaran nuestras imagenes
            flash.addFlashAttribute("info", "Has subido correctamente " + foto.getOriginalFilename() + "");//mensaje de exito
            cliente.setFoto(uniqueFilename);//pasamos el nombre dela foto al cliente, queda guardada en la db

        }
        //Crear Cliente
        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con Exito!" : "Cliente creado con exito!";
        clienteService.save(cliente);
        status.setComplete();//con este metodo elimina el objeto cliente de la seccion es buena practica para no colocar en el html hidden id
        flash.addFlashAttribute("success", mensajeFlash);//metodo para mostrar mensajes al ejecutar una accion en los botones
        return "redirect:listar";
    }


    //controlador para eliminar un elemento de la lista
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        if (id > 0) {
            //para eliminar una imagen se debe primero tener el cliente
            Cliente cliente = clienteService.findOne(id);
            clienteService.delete(id);//metodo eliminar en jpaRepository
            flash.addFlashAttribute("success", "Cliente Eliminado con exito");
            //imagen

            if (uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito!");
            }

        }
        return "redirect:/listar";
    }

    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();//obtenemos los auth
        //validamos si es distinto de null
        if (context == null) {
            return false;
        }
        Authentication auth = context.getAuthentication();
        if (auth == null) {
            return false;
        }
        //obtenemos una colecciones de roles
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();//creamos un generico para los users
        //forma reducida del foreac de abajo
        return authorities.contains(new SimpleGrantedAuthority(role));
        /*
        for(GrantedAuthority authority : authorities) {
            //obtenemos el rol
            if(role.equals(authority.getAuthority())){
                logger.info("Hola usuario ".concat(auth.getName()).concat(" tu rol es: ").concat(authority.getAuthority()));
                return true;
            }
        }
        return false;*/
    }
}
