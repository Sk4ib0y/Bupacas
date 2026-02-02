package com.bupacas.service;


import com.bupacas.model.Banco;

import java.util.List;

public interface BancoService
{
    List<Banco> getAll( );
    Banco getById(Integer id);
    Banco save(Banco banco);
    void delete(Integer id);
    Banco update(Integer id, Banco banco);
}
