package com.bupacas.service.impl;

import com.bupacas.model.Cliente;
import com.bupacas.repository.ClienteRepository;
import com.bupacas.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService
{
    private final ClienteRepository ClienteRepository;

    @Override
    public List<Cliente> getAll()
    {
        return ClienteRepository.findAll();
    }

    @Override
    public Cliente getById(Integer id)
    {
        return ClienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente save(Cliente Cliente)
    {
        return ClienteRepository.save(Cliente);
    }

    @Override
    public void delete(Integer id)
    {
        ClienteRepository.deleteById(id);
    }

    @Override
    public Cliente update(Integer id, Cliente Cliente) {
        Cliente aux = ClienteRepository.getById( id );
        Cliente.setRfc( Cliente.getRfc());
        Cliente.setNombre( Cliente.getNombre() );
        Cliente.setEmpresa( Cliente.getEmpresa() );
        Cliente.setZona( Cliente.getZona() );
        ClienteRepository.save( aux );
        return aux;
    }
}
