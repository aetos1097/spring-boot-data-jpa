package com.estudio.springbootdatajpa.models.service;

import com.estudio.springbootdatajpa.models.dao.IClienteDao;
import com.estudio.springbootdatajpa.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase de tipo Service que esta basada en el patron de disign Fachade
 * Gracias a esta clase podemos acceder a cualquier otra clase Dao que hayamos creado e utilizar su logica
 */
@Service
public class ClienteServiceImpl implements IClienteService {
    //inyectamos el cliente DAO
    @Autowired
    private IClienteDao clienteDao;
    @Override
    @Transactional(readOnly = true)// toma el contenido del metodo y lo envuelve dentro de una transaccion
    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }


    @Override
    @Transactional//sin readOnly porque este es de escritura
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }


    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clienteDao.findOne(id);
    }

    @Transactional//se deja asi porque estara actualizando la tabla
    @Override
    public void delete(Long id) {
        clienteDao.delete(id);
    }
}
