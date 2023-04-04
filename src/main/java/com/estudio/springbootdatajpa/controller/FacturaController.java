package com.estudio.springbootdatajpa.controller;

import com.estudio.springbootdatajpa.models.entity.Cliente;
import com.estudio.springbootdatajpa.models.entity.Factura;
import com.estudio.springbootdatajpa.models.entity.Producto;
import com.estudio.springbootdatajpa.models.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * En esta clase se pondrán los metodos controladores para las solicitudes http
 */
@Controller
@RequestMapping("/factura")//url base->http://localhost:8082/factura
@SessionAttributes("factura")//guarda los atributos hasta que se envie
public class FacturaController {

    @Autowired
    private IClienteService clienteService;//lo inyectamso porque necesitamos buscar por id

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value="clienteId") Long clienteId, Map<String,Object> model, RedirectAttributes flash){
        Cliente cliente = clienteService.findOne(clienteId);
        if(cliente == null){
            flash.addFlashAttribute("error","El cilente no existe en base de datos");
            return "redirect:/listar";
        }
        Factura factura = new Factura();
        factura.setCliente(cliente);//asiganamos el cliente a la factura
        model.put("factura", factura);
        model.put("titulo","Crear Factura");
        return "factura/form";
    }
    //controlador para cargar los productos
    @GetMapping(value="/cargar-productos/{term}", produces = {"application/json"})//produces={"application/json"} denota la salida(respuesta) de el metodo que sera en json
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        //suprime una vista de thymleaf y en ves de eso toma el resultado convertido en json y eso lo va a poblar en el body de la respuesta
        //y guarda le repuesta en el body
        return clienteService.findByNombre(term);
    }


}
