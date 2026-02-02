package com.bupacas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PapaDto {
    private Integer id;
    private String tipo;
    private String tamaño;
    private String variedad;
    private Integer idProducto;
    private Integer idProveedor;
}