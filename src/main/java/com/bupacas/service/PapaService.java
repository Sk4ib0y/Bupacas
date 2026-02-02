package com.bupacas.service;

import com.bupacas.model.Papa;

import java.util.List;

public interface PapaService
{
    List<Papa> getAll( );
    Papa getById(Integer id);
    Papa save(Papa edido);
    void delete(Integer id);
    Papa update(Integer id, Papa papa);
}
