package com.estudio.springbootdatajpa.models.service;

import com.estudio.springbootdatajpa.models.entity.Cliente;
import com.estudio.springbootdatajpa.models.entity.Factura;
import com.estudio.springbootdatajpa.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz que tomara todas las clases dao de manera general
 */
public interface IClienteService {
    public List<Cliente> findAll();
    public Page<Cliente>findAll(Pageable pageable);//interfaz que permite hacer una pagin plegable
    public void save(Cliente cliente);
    public Cliente findOne(Long id);
    public void delete(Long id);
    public List<Producto> findByNombre(String term);
    public void saveFactura(Factura factura);
    public Producto findProductoById(Long id);
    public Factura findFacturaById(Long id);
}
