package com.bupacas.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductoDto {
    private Integer id;
    private Integer cantidad;
    private BigDecimal costoGanancia;
    private String empaque;
    private String merma;
    private Integer idPedido;

}
