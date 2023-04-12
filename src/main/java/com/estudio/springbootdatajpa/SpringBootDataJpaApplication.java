package com.estudio.springbootdatajpa;

import com.estudio.springbootdatajpa.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {//esta implementacion es para que cuando se ejecute el programa siga las instrucciones del metodo run
    //se inyecta la dependencia donde estan los metodos
    @Autowired
    IUploadFileService uploadFileService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {//al ejecutarse ejecuta los dos metodos de abajo
        uploadFileService.deleteAll();//elimina directorio
        uploadFileService.init();//inicializa o crea directorio

        //Creamos nuestras password encriptadas
        String password= "12345";
        for(int i=0; i<2; i++){
            String bcryptPassword= passwordEncoder.encode(password);
            System.out.println(bcryptPassword);
        }
    }
}
