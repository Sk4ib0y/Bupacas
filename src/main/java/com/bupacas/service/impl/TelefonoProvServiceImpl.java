package com.bupacas.service.impl;

import com.bupacas.model.TelefonoProv;
import com.bupacas.repository.TelefonoProvRepository;
import com.bupacas.service.TelefonoProvService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TelefonoProvServiceImpl implements TelefonoProvService
{
    private final TelefonoProvRepository telefonoProvRepository;

    @Override
    public List<TelefonoProv> getAll()
    {
        return telefonoProvRepository.findAll();
    }

    @Override
    public TelefonoProv getById(Integer id)
    {
        return telefonoProvRepository.findById(id).orElse(null);
    }

    @Override
    public TelefonoProv save(TelefonoProv telefonoProv)
    {
        return telefonoProvRepository.save(telefonoProv);
    }

    @Override
    public void delete(Integer id)
    {
        telefonoProvRepository.deleteById(id);
    }

    @Override
    public TelefonoProv update(Integer id, TelefonoProv telefonoProv)
    {
        TelefonoProv aux = telefonoProvRepository.getById( id );
        telefonoProv.setTelefono(telefonoProv.getTelefono());
        telefonoProvRepository.save( aux );
        return aux;
    }
}
