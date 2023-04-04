package com.estudio.springbootdatajpa.models.service;

import com.estudio.springbootdatajpa.models.dao.IClienteDao;
import com.estudio.springbootdatajpa.models.dao.IProductoDao;
import com.estudio.springbootdatajpa.models.entity.Cliente;
import com.estudio.springbootdatajpa.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)// toma el contenido del metodo y lo envuelve dentro de una transaccion
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();//retorna un iterable se debe hace run cast
    }

    @Override
    @Transactional(readOnly = true)//solo sera lectura
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }


    @Override
    @Transactional//sin readOnly porque este es de escritura
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }


    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clienteDao.findById(id).orElse(null);//este devuelve un optional, es decir envuelve el resultado de la consulta
        //En este caso escojemos este metodo orElse para que si no lo encuentro retorne un null
    }

    @Transactional//se deja asi porque estara actualizando la tabla
    @Override
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    //metodo que hara la consulta por nombre
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
    }
}
