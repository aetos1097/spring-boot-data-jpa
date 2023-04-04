package com.estudio.springbootdatajpa.models.dao;

import com.estudio.springbootdatajpa.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interfaz que usara querys personalizados para poder usarlos en el autocomplete-productos
 */
public interface IProductoDao extends CrudRepository<Producto,Long> {
    @Query("select p from Producto p where p.nombre like %?1%")//el signo pregunta hace referencia al parametro-> %?1% = %term%
    public List<Producto> findByNombre(String term);
    //query para filtar por nombre y que ignore mayusculas o minisculas
    public List<Producto> findByNombreLikeIgnoreCase(String term);

}
