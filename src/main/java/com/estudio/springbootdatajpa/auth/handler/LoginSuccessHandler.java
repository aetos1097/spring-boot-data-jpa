package com.estudio.springbootdatajpa.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

/**
 * Clase para mostrar un mensaje del tipo flash cuando se incia sesion
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    //metodo parar agregar un mensaje cuando nos logeemos  con exito
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();
        flashMap.put("success", "Hola "+authentication.getName()+", haz  iniciado sesion con exito");
        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        //validar
        if(authentication != null){
            logger.info("El usuario '"+authentication.getName()+"' a iniciado con exito");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
