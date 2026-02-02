package com.bupacas.service.impl;

import com.bupacas.model.Banco_has_Pedido;
import com.bupacas.repository.Banco_has_PedidoRepository;
import com.bupacas.service.Banco_has_PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class Banco_has_PedidoServiceImpl implements Banco_has_PedidoService
{
    private final Banco_has_PedidoRepository banco_has_PedidoRepository;

    @Override
    public List<Banco_has_Pedido> getAll()
    {
        return banco_has_PedidoRepository.findAll();
    }

    @Override
    public Banco_has_Pedido getById(Integer id)
    {
        return banco_has_PedidoRepository.findById(id).orElse(null);
    }

    @Override
    public Banco_has_Pedido save(Banco_has_Pedido banco_has_Pedido)    {
        return banco_has_PedidoRepository.save(banco_has_Pedido);
    }

    @Override
    public void delete(Integer id)    {
        banco_has_PedidoRepository.deleteById(id);
    }

    @Override
    public Banco_has_Pedido update(Integer id, Banco_has_Pedido banco_has_Pedido)    {
        Banco_has_Pedido aux = banco_has_PedidoRepository.getById( id );
        banco_has_PedidoRepository.save( aux );
        return aux;
    }
}
