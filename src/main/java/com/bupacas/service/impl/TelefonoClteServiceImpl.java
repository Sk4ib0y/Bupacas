package com.bupacas.service.impl;

import com.bupacas.model.TelefonoClte;
import com.bupacas.repository.TelefonoClteRepository;
import com.bupacas.service.TelefonoClteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TelefonoClteServiceImpl implements TelefonoClteService
{
    private final TelefonoClteRepository telefonoClteRepository;

    @Override
    public List<TelefonoClte> getAll()
    {
        return telefonoClteRepository.findAll();
    }

    @Override
    public TelefonoClte getById(Integer id)
    {
        return telefonoClteRepository.findById(id).orElse(null);
    }

    @Override
    public TelefonoClte save(TelefonoClte telefonoClte)
    {
        return telefonoClteRepository.save(telefonoClte);
    }

    @Override
    public void delete(Integer id)
    {
        telefonoClteRepository.deleteById(id);
    }

    @Override
    public TelefonoClte update(Integer id, TelefonoClte telefonoClte)
    {
        TelefonoClte aux = telefonoClteRepository.getById( id );
        telefonoClte.setTelefono(telefonoClte.getTelefono());
        telefonoClteRepository.save( aux );
        return aux;
    }
}
