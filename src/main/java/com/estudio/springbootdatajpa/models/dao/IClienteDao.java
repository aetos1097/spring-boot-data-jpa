package com.estudio.springbootdatajpa.models.dao;

import com.estudio.springbootdatajpa.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interfaz para denotar los metodos Dao
 * Tenemos que poner todos los metodos que se encuentren en las clases dao es decir los metodos de crud,
 * pero cabe aclarar que las interfaces pueden heredar otras interfaces lo que significa que se puede usar
 * los methods embebidos en la interfaz padre, pero asi mismo se puede crear nuevos methods en la interfaz hija
 */
//en este aunque sea un servicio no se le asigna @Service porque es una clase especial que por debajo ya lo hace
public interface IClienteDao extends JpaRepository<Cliente, Long> {//heredamos de crud repository, pero como necesitamos paginar tenemos que extender de Jpa Repository

    @Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")//el 1 quiere decir asignacion del Long id
    public  Cliente fetchByIdWithFacturas(Long id);


}
