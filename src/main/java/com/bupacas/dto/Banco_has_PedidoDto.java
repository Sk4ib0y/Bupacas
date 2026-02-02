package com.bupacas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Banco_has_PedidoDto {
    private Integer id;
    private Integer idBanco;
    private Integer idPedido;
}