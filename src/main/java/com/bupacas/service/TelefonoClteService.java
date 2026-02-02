package com.bupacas.service;

import com.bupacas.model.TelefonoClte;

import java.util.List;

public interface TelefonoClteService
{
    List<TelefonoClte> getAll( );
    TelefonoClte getById(Integer id);
    TelefonoClte save(TelefonoClte telefonoClte);
    void delete(Integer id);
    TelefonoClte update(Integer id, TelefonoClte telefonoClte);
}
