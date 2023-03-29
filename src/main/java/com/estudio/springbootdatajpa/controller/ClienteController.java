package com.estudio.springbootdatajpa.controller;

import com.estudio.springbootdatajpa.models.dao.IClienteDao;
import com.estudio.springbootdatajpa.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
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
        Cliente cliente = new Cliente();//creo una nueva innstancia

        model.put("cliente", cliente);
        model.put("titulo","Formulario de cliente");
        return "form";
    }//guardamos los eleementos
    @PostMapping("/form")
    public String guardar(Cliente cliente){
        clienteDao.save(cliente);
        return  "redirect:listar";
    }
}
