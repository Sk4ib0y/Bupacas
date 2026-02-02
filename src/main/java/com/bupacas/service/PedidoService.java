package com.bupacas.service;


import com.bupacas.model.Pedido;

import java.util.List;

public interface PedidoService
{
    List<Pedido> getAll( );
    Pedido getById(Integer id);
    Pedido save(Pedido edido);
    void delete(Integer id);
    Pedido update(Integer id, Pedido pedido);
}
