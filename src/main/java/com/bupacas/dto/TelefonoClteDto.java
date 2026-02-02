package com.bupacas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelefonoClteDto {
    private Integer id;
    private String telefono;
    private Integer idClte;

}
