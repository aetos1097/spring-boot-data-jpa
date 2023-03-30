package com.estudio.springbootdatajpa.models.dao;

import com.estudio.springbootdatajpa.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interfazz para denotar los metodos Dao
 * Tenemos que poner todos los metodos que se encuentren en las clases dao es decir los metodos de crud
 */
//en este aunque sea un servicio no se le asigna @Service porque es una clase especial que por debajo ya lo hace
public interface IClienteDao extends CrudRepository<Cliente, Long> {//heredamos de crud repository


}
