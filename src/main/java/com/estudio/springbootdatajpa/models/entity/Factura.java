package com.estudio.springbootdatajpa.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="facturas")
public class Factura implements Serializable {//toda clas entitiy como buena practica debe implementar esta inerfaz
    @Id()
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String descripcion;
    private String observacion;
    @Temporal(TemporalType.DATE)
    @Column(name="create_at")
    private Date createAt;
    @ManyToOne(fetch=FetchType.LAZY)/*muchas facturas a un cliente es decir many=desde facturas a one=cliente
    es decir muchas facturas pueden tener un cliente
    fetch=FetchType.LAZY ayuda a que la consulta traiga solo lo necesario y no todos los datos*/
    private Cliente cliente;

     @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
     @JoinColumn(name="factura_id")//condicion para que se enlaze unidireccionalmente a la tabla itemFactura
     private List<ItemFactura> items;
    //antes de guardar la factura se le asigna la fecha
    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

    public Factura() {
        this.items=new ArrayList<ItemFactura>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    //agrega los items de la factura que se crean en los controladores
    public void addItemFactura(ItemFactura item) {
        this.items.add(item);
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }

    public Double getTotal(){
        Double total =0.0;
        int size = items.size();
        for(int i = 0; i < size; i++){
            total += items.get(i).calcularImporte();
        }
        return total;
    }

    //como estamos usando serializable nitamos el atributo
    private static final long serialVersionUID = 1L;
}
