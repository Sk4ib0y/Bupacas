package com.bupacas.service.impl;

import com.bupacas.model.Papa;
import com.bupacas.repository.PapaRepository;
import com.bupacas.service.PapaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PapaServiceImpl implements PapaService
{
    private final PapaRepository papaRepository;

    @Override
    public List<Papa> getAll()
    {
        return papaRepository.findAll();
    }

    @Override
    public Papa getById(Integer id)
    {
        return papaRepository.findById(id).orElse(null);
    }

    @Override
    public Papa save(Papa papa)
    {
        return papaRepository.save(papa);
    }

    @Override
    public void delete(Integer id)
    {
        papaRepository.deleteById(id);
    }

    @Override
    public Papa update(Integer id, Papa papa) {
        Papa aux = papaRepository.getById( id );
        papa.setTipo( papa.getTipo());
        papa.setTamaño( papa.getTamaño() );
        papa.setVariedad( papa.getVariedad() );
        papa.setIdProducto( papa.getIdProducto() );
        papa.setIdProveedor( papa.getIdProveedor() );
        papaRepository.save( aux );
        return aux;
    }
}
