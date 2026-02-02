package com.bupacas.service;

import com.bupacas.model.Cliente;

import java.util.List;

public interface ClienteService
{
    List<Cliente> getAll( );
    Cliente getById(Integer id);
    Cliente save(Cliente cliente);
    void delete(Integer id);
    Cliente update(Integer id, Cliente cliente);
}
