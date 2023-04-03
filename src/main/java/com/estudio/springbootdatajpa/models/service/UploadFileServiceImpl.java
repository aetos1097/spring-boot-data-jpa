package com.estudio.springbootdatajpa.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Clase que maneja la logicc=a de las acciones con la foto que se sube
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
    //Copia la imagen en la aplicacion
    @Override
    public String copy(MultipartFile file) throws IOException {//copiar la foto
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();//reescribimos el nombre de la foto a un nombreunico
        Path rootPath = getPath(uniqueFilename);

        log.info("rootPath: " + rootPath);

            /*
            Path directorioRecursos= Paths.get("src//main//resources//static//uploads");//path se importa de interfaz nio
//            String rootPath=directorioRecursos.toFile().getAbsolutePath();//con este objeto string ya podemos mover la imagen del directorio
            Esta linea se cambia para colocar una ruta absoluta en el path
            String rootPath="C://Temp//uploads";//esta es par a una ruta externa separada al proyecto*/

        Files.copy(file.getInputStream(), rootPath);/* esta línea de código copia el contenido de un archivo cargado en el servidor web a
        una ubicación específica en el sistema de archivos local*/

        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {//eliminar la foto
        //imagen
        Path rootPath = getPath(filename);//obtenemso el Path entero de la imagen
        File archivo= rootPath.toFile();//convertimos el path a archivo para buscarlo en la carpeta
        //comprabamos que el archivo se pueda leer y exista
        if(archivo.exists() && archivo.canRead()){
            if(archivo.delete()){//archivo.delete borra el archivo y devuelve un boolean
                return true;
            }
        }
        return false;
    }
    //metodos para crear y eliminar direcctorias al arranque de la app
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());//elimina cada directorio del tipo UPLOADS_FOLDER = "uploads"
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }

    public Path getPath(String filename) {//obtenemos el pathAbsoluto
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}
