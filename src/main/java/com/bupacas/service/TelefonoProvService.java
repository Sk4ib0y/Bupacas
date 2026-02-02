package com.bupacas.service;

import com.bupacas.model.TelefonoProv;
import java.util.List;

public interface TelefonoProvService
{
    List<TelefonoProv> getAll( );
    TelefonoProv getById(Integer id);
    TelefonoProv save(TelefonoProv telefonoProv);
    void delete(Integer id);
    TelefonoProv update(Integer id, TelefonoProv telefonoProv);
}
