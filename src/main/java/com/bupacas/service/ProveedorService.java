package com.bupacas.service;


import com.bupacas.model.Proveedor;

import java.util.List;

public interface ProveedorService
{
    List<Proveedor> getAll( );
    Proveedor getById(Integer id);
    Proveedor save(Proveedor proveedor);
    void delete(Integer id);
    Proveedor update(Integer id, Proveedor proveedor);
}
