package com.example.bupacas.Endpoints.DTO;

import com.google.gson.annotations.SerializedName;

public class ProveedorDTO {

    @SerializedName("id")
    private Integer idProveedor;   // o int id si es primitivo

    @SerializedName("rfc")         // ← nombre REAL en JSON
    private String rfc_prov;

    @SerializedName("nombre")
    private String nombre_prov;

    @SerializedName("empresa")
    private String empresa_prov;

    @SerializedName("zona")
    private String zona_prov;

    public Integer getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Integer idProveedor) { this.idProveedor = idProveedor; }

    public String getRFC_prov() { return rfc_prov; }
    public void setRFC_prov(String rfc_prov) { this.rfc_prov = rfc_prov; }

    @Override
    public String toString() {
        return "ProveedorDTO{" +
                "idProveedor=" + idProveedor +
                ", rfc_prov='" + rfc_prov + '\'' +
                ", nombre_prov='" + nombre_prov + '\'' +
                ", empresa_prov='" + empresa_prov + '\'' +
                ", zona_prov='" + zona_prov + '\'' +
                '}';
    }

    public String getNombre_prov() { return nombre_prov; }
    public void setNombre_prov(String nombre_prov) { this.nombre_prov = nombre_prov; }

    public String getEmpresa_prov() { return empresa_prov; }
    public void setEmpresa_prov(String empresa_prov) { this.empresa_prov = empresa_prov; }

    public String getZona_prov() { return zona_prov; }
    public void setZona_prov(String zona_prov) { this.zona_prov = zona_prov; }

}
