package com.estudio.springbootdatajpa.controller;

import com.estudio.springbootdatajpa.models.dao.IClienteDao;
import com.estudio.springbootdatajpa.models.entity.Cliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
@SessionAttributes("cliente")//indicamos que se va a guardar en los atributos de la seccion del objeto cliente
public class ClienteController {
    @Autowired
    private IClienteDao clienteDao;
    //listar los elementos
    @RequestMapping(value = "/listar",method= RequestMethod.GET)//lo mismo que el get
    public String listar(Model model){
        model.addAttribute("titulo","Listado de Clientes");
        model.addAttribute("clientes",clienteDao.findAll());
        return "listar";
    }
    //crear elementos
    @GetMapping("/form")
    public String crear(Map<String,Object> model){
        Cliente cliente = new Cliente();//creo una nueva instancia

        model.put("cliente", cliente);
        model.put("titulo","Formulario de cliente");
        return "form";
    }

    //agregar o editar un elemento
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value="id") Long id, Map<String,Object> model){
        Cliente cliente = null;
        //validamos que el id sea mayo 0
        if(id > 0){
            cliente=clienteDao.findOne(id);
        }else{
            return "redirect:/listar";
        }
        model.put("cliente",cliente);
        model.put("titulo","Editar cliente");
        return "form";
    }
    //guardamos los eleementos
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status){//colocamos la anotacion Valid en el argumento porque sera lo que se envia y debe estar validado
        //Siempre van juntos El objeto con @Valid y el BindingResult y de resto lo demas
        //El BindinfResult es una interfaz que captura los errores en las validaciones
        //Ahora validamos con un if si hay algun error en los campos del formulario
        if(result.hasErrors()){
            //si el objeto se llama igual que en el formulario con metodo get entonces se pasa automatico
            //si no se debe poner en los argumentos :(@Valid @ModelAttribute Cliente cliente, BindingResult result,Model model)
            model.addAttribute("titulo","Formulario Cliente");
            return "form";//si hay errores nos devolvemos al formulario con ruta /form
        }
        clienteDao.save( cliente);
        status.setComplete();//con este metodo elimina el objeto cliente de la seccion es buena practica para no colocar en el html hidden id
        return  "redirect:listar";
    }
}
