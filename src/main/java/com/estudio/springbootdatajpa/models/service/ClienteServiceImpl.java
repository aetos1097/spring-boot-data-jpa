package com.estudio.springbootdatajpa.models.service;

import com.estudio.springbootdatajpa.models.dao.IClienteDao;
import com.estudio.springbootdatajpa.models.dao.IFacturaDao;
import com.estudio.springbootdatajpa.models.dao.IProductoDao;
import com.estudio.springbootdatajpa.models.entity.Cliente;
import com.estudio.springbootdatajpa.models.entity.Factura;
import com.estudio.springbootdatajpa.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase de tipo Service que esta basada en el patron de disign Fachade
 * Gracias a esta clase podemos acceder a cualquier otra clase Dao que hayamos creado y utilizar su logica
 */
@Service
public class ClienteServiceImpl implements IClienteService {
    //inyectamos el cliente DAO
    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao;

    //inyectamos el metodo que hay dentro de la interfaz factura
    @Autowired
    private IFacturaDao facturaDao;

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

    @Transactional
    @Override
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly =true)
    public Producto findProductoById(Long id) {
        //esta consulta retorna un optional por eso necesita un orElse
        return productoDao.findById(id).orElse(null);
    }

    @Transactional(readOnly =true)
    @Override
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaDao.deleteById(id);

    }
    @Transactional(readOnly = true)
    @Override
    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente fetchByIdWithFacturas(Long id) {
        return clienteDao.fetchByIdWithFacturas(id);
    }
}
