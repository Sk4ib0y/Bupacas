package com.bupacas.service;


import com.bupacas.model.Producto;

import java.util.List;

public interface ProductoService
{
    List<Producto> getAll( );
    Producto getById(Integer id);
    Producto save(Producto producto);
    void delete(Integer id);
    Producto update(Integer id, Producto producto);
}
