package com.bupacas.repository;

import com.bupacas.model.Banco_has_Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Banco_has_PedidoRepository extends JpaRepository<Banco_has_Pedido, Integer>{
}