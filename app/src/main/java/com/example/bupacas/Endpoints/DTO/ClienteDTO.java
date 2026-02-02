package com.example.bupacas.Endpoints.DTO;

import com.google.gson.annotations.SerializedName;

public class ClienteDTO
{
    @SerializedName("id")
    private Integer idCliente;
    @SerializedName("rfc")
    private String rfc_Clte;

    @SerializedName("nombre")
    private String nombre_Clte;

    @SerializedName("empresa")
    private String empresa_Clte;

    @SerializedName("zona")
    private String zona_Clte;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "idCliente=" + idCliente +
                ", rfc_Clte='" + rfc_Clte + '\'' +
                ", nombre_Clte='" + nombre_Clte + '\'' +
                ", empresa_Clte='" + empresa_Clte + '\'' +
                ", zona_Clte='" + zona_Clte + '\'' +
                '}';
    }

    public String getRfc_Clte() {
        return rfc_Clte;
    }

    public void setRfc_Clte(String rfc_Clte) {
        this.rfc_Clte = rfc_Clte;
    }

    public String getNombre_Clte() {
        return nombre_Clte;
    }

    public void setNombre_Clte(String nombre_Clte) {
        this.nombre_Clte = nombre_Clte;
    }

    public String getEmpresa_Clte() {
        return empresa_Clte;
    }

    public void setEmpresa_Clte(String empresa_Clte) {
        this.empresa_Clte = empresa_Clte;
    }

    public String getZona_Clte() {
        return zona_Clte;
    }

    public void setZona_Clte(String zona_Clte) {
        this.zona_Clte = zona_Clte;
    }
}
