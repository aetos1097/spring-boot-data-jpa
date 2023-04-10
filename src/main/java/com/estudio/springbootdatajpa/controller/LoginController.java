package com.estudio.springbootdatajpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
                        @RequestParam(value="logout", required=false) String logout,
            Model model, Principal principal, RedirectAttributes flash){//el principal permite la validacion
        if(principal != null){
            flash.addFlashAttribute("info","Ya a iniciado seccion anteriormente");
            return "redirect:/";//evita que se haga doblemente el inicio de seccion
        }

        if(error != null){
            model.addAttribute("error","Error en el login: Nombre de usuario o contraseña incorrectos, vuelve a intentarlo");
        }

        if(logout != null){
            model.addAttribute("success","Has cerrado con exito!");
        }
        return "login";
    }
}
