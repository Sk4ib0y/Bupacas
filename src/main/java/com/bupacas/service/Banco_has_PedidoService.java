package com.bupacas.service;

import com.bupacas.model.Banco_has_Pedido;
import java.util.List;

public interface Banco_has_PedidoService {
    List<Banco_has_Pedido> getAll( );
    Banco_has_Pedido getById(Integer id);
    Banco_has_Pedido save(Banco_has_Pedido banco_has_Pedido);
    void delete(Integer id);
    Banco_has_Pedido update(Integer id, Banco_has_Pedido banco_has_Pedido);
}