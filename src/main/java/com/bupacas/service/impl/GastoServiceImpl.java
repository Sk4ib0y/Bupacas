package com.bupacas.service.impl;

import com.bupacas.model.Gasto;
import com.bupacas.repository.GastoRepository;
import com.bupacas.service.GastoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GastoServiceImpl implements GastoService
{
    private final GastoRepository gastoRepository;

    @Override
    public List<Gasto> getAll()
    {
        return gastoRepository.findAll();
    }

    @Override
    public Gasto getById(Integer id)
    {
        return gastoRepository.findById(id).orElse(null);
    }

    @Override
    public Gasto save(Gasto gasto)
    {
        return gastoRepository.save(gasto);
    }

    @Override
    public void delete(Integer id)
    {
        gastoRepository.deleteById(id);
    }

    @Override
    public Gasto update(Integer id, Gasto gasto)
    {
        Gasto aux = gastoRepository.getById( id );
        gasto.setCantidad(gasto.getCantidad());
        gasto.setTipo(gasto.getTipo());
        gastoRepository.save( aux );
        return aux;
    }
}
