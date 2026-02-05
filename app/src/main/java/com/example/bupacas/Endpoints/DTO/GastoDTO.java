package com.example.bupacas.Endpoints.DTO;

import java.math.BigDecimal;

public class GastoDTO
{
    Integer id;
    BigDecimal cantidad;
    String tipo;
    private Integer idBanco;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }
}
