package com.estudio.springbootdatajpa.models.dao;

import com.estudio.springbootdatajpa.models.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository//Marca la clase como un componente de persistencia
public class ClienteDaoImpl implements IClienteDao {

    @PersistenceContext//de forma automatica inyecta el entityManager segun  la configracion de la unidad de depedencia que contiene el proveedor de jpa
    private EntityManager em;// se encarga de manejar las clases de entidades
    @Transactional(readOnly = true)// toma el contenido del metodo y lo envuelve dentro de una trasaccion
    @Override
    public List findAll() {
        return em.createQuery("from Cliente").getResultList();// aparece error pero si funciona
    }
    //metodo que toma el objeto cliente y lo guarda
    @Override
    @Transactional//sin readOnly porque este es de escritura
    public void save(Cliente cliente) {
        em.persist(cliente);//em.persist es un metodo de el entytManager para guardar los datos
    }
}
