package com.estudio.springbootdatajpa.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Clase que maneja la logicca de las acciones con la foto que se sube
 */

@Service
public class UploadFileServiceImpl implements IUploadFileService{
    private final Logger log = LoggerFactory.getLogger(getClass());// con esto podemos mostrar por consola
    private final static String UPLOADS_FOLDER = "uploads";//variable para no usar siempre la palabra uploads

    @Override
    public Resource load(String filename) throws MalformedURLException {//cargar la foto
        Path pathFoto = getPath(filename);//tomara el path de la imagen
        log.info("pathFoto:" + pathFoto);//mostrara el path por consola
        Resource recurso = null;

        recurso = new UrlResource(pathFoto.toUri());//se carga la imagen
        if (!recurso.exists() && !recurso.isReadable()) {
            throw new RuntimeException("Error: no se pudo cargar la imagen " + pathFoto.toString());
        }

        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {//ccopiar la foto
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();//reescribimos el nombre de la foto a un nombreunico
        Path rootPath = getPath(uniqueFilename);

        log.info("rootPath: " + rootPath);

            /*
            Path directorioRecursos= Paths.get("src//main//resources//static//uploads");//path se importa de interfaz nio
//            String rootPath=directorioRecursos.toFile().getAbsolutePath();//con este objeto string ya podemos mover la imagen del directorio
            Esta linea se cambia para colocar una ruta absoluta en el path
            String rootPath="C://Temp//uploads";//esta es par a una ruta externa separada al proyecto*/

        Files.copy(file.getInputStream(), rootPath);

        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {//eliminar la foto
        //imagen
        Path rootPath = getPath(filename);//obtenemso el Path entero de la imagen
        File archivo= rootPath.toFile();
        //comprabamos que el archivo se pueda leer y exista
        if(archivo.exists() && archivo.canRead()){
            if(archivo.delete()){
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename) {//obtenemos el path
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
