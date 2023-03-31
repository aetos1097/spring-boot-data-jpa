package com.estudio.springbootdatajpa.controller;

import com.estudio.springbootdatajpa.controller.util.paginator.PageRender;
import com.estudio.springbootdatajpa.models.dao.IClienteDao;
import com.estudio.springbootdatajpa.models.entity.Cliente;
import com.estudio.springbootdatajpa.models.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * Clase donde iran los controladores
 */
@Controller
@SessionAttributes("cliente")//indicamos que se va a guardar en los atributos de la seccion del objeto cliente
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    // ya no inyectamos el Dao si no la fachada IClienteService private IClienteDao clienteDao;
    //listar los elementos
    @RequestMapping(value = "/listar", method = RequestMethod.GET)//lo mismo que el get
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        /*se coloca como paramtro de entrada @RequestParam(name="page", defaultValue="0")int page
        para la paginacion al momento de listar*/
        Pageable pageRequest = PageRequest.of(page, 5);/*se importa la interfaz y con el metodo  PageRequest.of(page,5)
        Damos el parametro de entrada page y cuantos elementos queremos mostrar(0)
        */
        Page<Cliente> clientes = clienteService.findAll(pageRequest);//realizamos el servicio de filtrado
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page",pageRender);
    // ya no se usa esta linea porque se quiere traer pero paginar los clientes model.addAttribute("clientes",clienteService.findAll());
        return "listar";
    }


    //crear elementos
    @GetMapping("/form")
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();//creo una nueva instancia

        model.put("cliente", cliente);
        model.put("titulo", "Formulario de cliente");
        return "form";
    }

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


    //guardamos los elementos
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {//colocamos la anotacion Valid en el argumento porque sera lo que se envia y debe estar validado
        /*Siempre van juntos El objeto con @Valid y el BindingResult y de resto lo demas
         El BindinfResult es una interfaz que captura los errores en las validaciones
        Ahora validamos con un if si hay algun error en los campos del formulario*/
        if (result.hasErrors()) {
            /*si el objeto se llama igual que en el formulario con metodo get entonces se pasa automatico
            //si no se debe poner en los argumentos :(@Valid @ModelAttribute Cliente cliente, BindingResult result,Model model)*/
            model.addAttribute("titulo", "Formulario Cliente");
            return "form";//si hay errores nos devolvemos al formulario con ruta /form
        }
        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con Exito!" : "Cliente creado con exito!";
        clienteService.save(cliente);
        status.setComplete();//con este metodo elimina el objeto cliente de la seccion es buena practica para no colocar en el html hidden id
        flash.addFlashAttribute("success", mensajeFlash);//metodo para mostrar mensajes al ejecutar una accion en los botones
        return "redirect:listar";
    }

    //controlador para eliminar un elemento de la lista
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        if (id > 0) {
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente Eliminado cocn exito");
        }
        return "redirect:/listar";
    }
}
