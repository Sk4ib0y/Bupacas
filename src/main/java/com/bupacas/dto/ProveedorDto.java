package com.bupacas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProveedorDto {
    private Integer id;
    private String rfc;
    private String nombre;
    private String empresa;
    private String zona;
}
