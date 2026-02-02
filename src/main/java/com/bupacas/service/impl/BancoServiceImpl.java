package com.bupacas.service.impl;

import com.bupacas.model.Banco;
import com.bupacas.repository.BancoRepository;
import com.bupacas.service.BancoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BancoServiceImpl implements BancoService
{
    private final BancoRepository bancoRepository;

    @Override
    public List<Banco> getAll()
    {
        return bancoRepository.findAll();
    }

    @Override
    public Banco getById(Integer id)
    {
        return bancoRepository.findById(id).orElse(null);
    }

    @Override
    public Banco save(Banco banco)
    {
        return bancoRepository.save(banco);
    }

    @Override
    public void delete(Integer id)
    {
        bancoRepository.deleteById(id);
    }

    @Override
    public Banco update(Integer id, Banco banco)
    {
        Banco aux = bancoRepository.getById( id );
        banco.setCantidad(banco.getCantidad());
        banco.setTipo(banco.getTipo());
        banco.setEstado(banco.getEstado());
        bancoRepository.save( aux );
        return aux;
    }
}
