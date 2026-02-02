package com.bupacas.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class PedidoDto {
    private Integer id;
    private String codPostal;
    private String destino;
    private Date fecha;
    private Integer idCliente;
    private Integer idProveedor;
}