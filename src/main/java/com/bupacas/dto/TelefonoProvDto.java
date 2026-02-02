package com.bupacas.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TelefonoProvDto {
    private Integer id;
    private String telefono;
    private Integer idProv;

}
