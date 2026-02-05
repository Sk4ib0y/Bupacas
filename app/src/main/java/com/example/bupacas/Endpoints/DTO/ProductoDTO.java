package com.example.bupacas.Endpoints.DTO;

import java.math.BigDecimal;

public class ProductoDTO
{
    private Integer id;
    private Integer cantidad;
    private BigDecimal costoGanancia;
    private String empaque;
    private String merma;
    private Integer idPedido;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoGanancia() {
        return costoGanancia;
    }

    public void setCostoGanancia(BigDecimal costoGanancia) {
        this.costoGanancia = costoGanancia;
    }

    public String getEmpaque() {
        return empaque;
    }

    public void setEmpaque(String empaque) {
        this.empaque = empaque;
    }

    public String getMerma() {
        return merma;
    }

    public void setMerma(String merma) {
        this.merma = merma;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
}
