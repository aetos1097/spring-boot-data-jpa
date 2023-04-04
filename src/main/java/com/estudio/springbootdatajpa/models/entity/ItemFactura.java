package com.estudio.springbootdatajpa.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="producto_id")
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Double calcularImporte(){

        return  cantidad.doubleValue()* producto.getPrecio();//cantidad.doubleValue()-> toma el valor de catindad en int y lo pasa a double
    }
    //luego de hacer los servicios para los productos si generamos los get and setters


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    private static final long serialVersionUID = 1L;
}
