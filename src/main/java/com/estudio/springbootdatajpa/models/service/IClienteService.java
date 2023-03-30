package com.estudio.springbootdatajpa.models.service;

import com.estudio.springbootdatajpa.models.entity.Cliente;

import java.util.List;

/**
 * Interfaz que tomara todas las clases dao de manera general
 */
public interface IClienteService {
    public List<Cliente> findAll();
    public void save(Cliente cliente);
    public Cliente findOne(Long id);
    public void delete(Long id);
}
