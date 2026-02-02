package com.bupacas.service.impl;

import com.bupacas.model.Pedido;
import com.bupacas.repository.PedidoRepository;
import com.bupacas.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService
{
    private final PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> getAll()
    {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido getById(Integer id)
    {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public Pedido save(Pedido pedido)
    {
        return pedidoRepository.save(pedido);
    }

    @Override
    public void delete(Integer id)
    {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido update(Integer id, Pedido pedido) {
        Pedido aux = pedidoRepository.getById( id );
        pedido.setCodPostal( pedido.getCodPostal());
        pedido.setDestino( pedido.getDestino() );
        pedido.setFecha( pedido.getFecha() );
        pedido.setIdCliente( pedido.getIdCliente() );
        pedido.setIdProveedor( pedido.getIdProveedor() );
        pedidoRepository.save( aux );
        return aux;
    }
}
