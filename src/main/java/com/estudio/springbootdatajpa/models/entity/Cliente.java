package com.estudio.springbootdatajpa.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity //para tablas
@Table(name = "clientes")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//indica que sera autoincrementable el id
    private Long id;//este atributo es la llave
    //se puede colocar la anotacion @Column para cambiar el nombre,tamanio,nullable(cutomizar) a una variable al de la tabla en la db
    @NotEmpty//esta anotacion valida que no este vacion peor SOLO con Strings
    //@Size(min=1,max=20) permite darle un tamanio al campo de caracteres
    private String nombre;

    @NotEmpty
    private String apellido;

    @NotEmpty
    @Email//Validacion por correo
    private String email;

    @NotNull//Todos los demas tipos no STRING se pueden validar con esta anotacion
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)//solamente para fechas, indica el formato en que se gurdara esta fecha en la tabla
    @DateTimeFormat(pattern = "yyyy-MM-dd") //asignamos el aptron de fecha como lo deseemos
    private Date createAt;
    /*
    Ya no sera necesario este metodo porque se creara un nuevo campo en el html
    //@anotacion en la entidad que creara la fecha justo antes de insertar el registro en la base de datos
    @PrePersist
    public void prePersisit(){
        //metodo para registrar antes que se guarde a la base de datos
        createAt = new Date();
    }*/
    private static final long serialVersionUID = 1L;//esta línea de código sirve para mantener la compatibilidad en la serialización de objetos en Java al proporcionar un identificador único a cada clase serializable.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
