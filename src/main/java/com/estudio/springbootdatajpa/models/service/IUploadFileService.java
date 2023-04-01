package com.estudio.springbootdatajpa.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUploadFileService {
    public Resource load(String filename) throws MalformedURLException; //mostrar la imagen
    public String copy(MultipartFile file) throws IOException;
    public boolean delete(String filename);
}
