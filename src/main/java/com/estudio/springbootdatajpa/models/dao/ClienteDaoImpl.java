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
    //Listamos solo un id
    @Override
    public Cliente findOne(Long id) {
        return em.find(Cliente.class,id);//Gracias al EntityManager se puede usar el metodo find
    }
    //metodo que toma el objeto cliente y lo guarda
    @Override
    @Transactional//sin readOnly porque este es de escritura
    public void save(Cliente cliente) {//este es tanto para guardar como para editar
        /*Validacion para que se pueda guardar o editar un campo o el cliente
        * para esto se mira si el cliente es distinto de nulo y mayor a 0
        * si es asi se usa el metodo merge de EntityManager para mexclar los datos anteriores con los nuevos y los guarda
        * pero si es un usuario nuevo solo guarde lo que envia ya que el id viene como nulo*/
        if(cliente.getId() != null && cliente.getId() >0){
            em.merge(cliente);
        }else {
            em.persist(cliente);//em.persist es un metodo de el entytManager para guardar los datos
        }
    }

    @Override
    @Transactional//se deja asi porque estara actualizando la tabla
    public void delete(Long id) {
        //primero obtenemos el cliente que queremos eliminar
        //Cliente cliente = findOne(id);
        //em.remove(cliente) podemos colocar esta linea o simplifcar lo con:
        em.remove(findOne(id));
    }
}
