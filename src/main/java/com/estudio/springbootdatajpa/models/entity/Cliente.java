package com.estudio.springbootdatajpa.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity //para tablas
@Table(name = "clientes")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//indica que sera autoincrementable el id
    private Long id;//este atributo es la llave
    //se puede colocar la anotacion @Column para cambiar el nombre,tamanio,nullable(cutomizar) a una variable al de la tabla en la db
    private String nombre;
    private String apellido;
    private String email;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)//solamente para fechas, indica el formato en que se gurdara esta fecha en la tabla
    private Date createAt;
    //@anotacion en la entidad que creara la fecha justo antes de insertar el registro en la base de datos
    @PrePersist
    public void prePersisit(){
        //metodo para registrar antes que se guarde a la base de datos
        createAt = new Date();
    }
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
