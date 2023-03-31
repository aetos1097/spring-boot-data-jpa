package com.estudio.springbootdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        String resourcePath= Paths.get("uploads").toAbsolutePath().toUri().toString();//variable para que el path sea dinamico
        log.info("resourcePath");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourcePath);//ruta de carpeta externa del programa(Esta se debe crear)
        //.addResourceLocations("file:C:/Temp/uploads/") path fijo no dinamico
    }
}
