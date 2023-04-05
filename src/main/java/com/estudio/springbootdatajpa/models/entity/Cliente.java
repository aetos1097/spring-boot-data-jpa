package com.estudio.springbootdatajpa.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private String foto;

    @OneToMany(mappedBy ="cliente" ,fetch=FetchType.LAZY,cascade=CascadeType.ALL)/*fetch=FetchType.LAZY ayuda a que la consulta traiga solo lo necesario y no todos lo datos
    mappedBy="cliente" anuncia que sera bidireccional es decir que en facturas hay un elemento cliente y en cliente hay un elemento facturas
    y al mismo tiempo crea la llave foranea cliente_id en facturas
    cascade=CascadeType.ALL-> ayuda a que las operaciones relacionadas a este campo se pasen asus atributos hijos*/
    private List<Factura> facturas;

    public Cliente() {
        facturas = new ArrayList<Factura>();//crea los objetos por cada cliente
    }

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Factura> getFacturas() {
        //cada que se ejecuta este metodo, se hace una consulta a facturas
        return facturas;
    }
    //se guardan todas
    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }
    //se guarda una por una de las facturas
    public void addFactura(Factura factura) {
        facturas.add(factura);
    }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }

    private static final long serialVersionUID = 1L;//esta línea de código sirve para mantener la compatibilidad en la serialización de objetos en Java al proporcionar un identificador único a cada clase serializable.

}
