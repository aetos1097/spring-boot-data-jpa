package com.estudio.springbootdatajpa.models.dao;

import com.estudio.springbootdatajpa.models.entity.Cliente;

import java.util.List;

/**
 * Interfazz para denotar los metodos Dao
 * Tenemos que poner todos los metodos que se encuentren en las clases dao es decir los metodos de crud
 */
public interface IClienteDao {
    public List<Cliente> findAll();
    public void save(Cliente cliente);
    public Cliente findOne(Long id);
    public void delete(Long id);

}
