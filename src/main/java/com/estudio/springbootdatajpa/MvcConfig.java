package com.estudio.springbootdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.file.Paths;
import java.util.Locale;

/**
 * Esta clase ayuda a mostrar la imagen despues de que se haya subido al servido
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //private final Logger log = LoggerFactory.getLogger(getClass());
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//
//        String resourcePath= Paths.get("uploads").toAbsolutePath().toUri().toString();//variable para que el path sea dinamico
//        log.info("resourcePath");
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations(resourcePath);//ruta de carpeta externa del programa(Esta se debe crear)
//        //.addResourceLocations("file:C:/Temp/uploads/") path fijo no dinamico
//    }
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //se hace un viewController que cargara solo la vista error(Controlador)
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/error_403").setViewName("error_403");
    }


    //configuracion de idioma
    //el primer bean muestra donde se va a almacenar el parametro de nuestro lenguaje
    //y se va a guardar en la seccion
    @Bean//es un bean de spring
    public LocaleResolver localeResolver(){
        //implementacion
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es","ES"));
        return localeResolver;
    }

    //Crear el interceptor
    //El segundo bean es el interceptor que se encarga de cambiar el lenguaje cada vez que se pase lang por url
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();//creamos la instancia
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;

    }
    //toca registrar este bean en la aplicacion

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());//recordar que se toma el objeto devvuelto por el bean
    }
}
