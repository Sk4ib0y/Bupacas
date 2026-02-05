package com.example.bupacas.Endpoints.DTO;

public class TelefonoClienteDTO {
    private Integer id;
    private String telefono;
    private Integer idClte;

    public Integer getIdClte() {
        return idClte;
    }

    public void setIdClte(Integer idClte) {
        this.idClte = idClte;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
