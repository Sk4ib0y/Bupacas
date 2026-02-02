package com.bupacas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Proveedor")
public class Proveedor {

    @Id
    @Column(name = "idProveedor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "RFC_prov")
    private String rfc;

    @Column(name = "nombre_prov")
    private String nombre;

    @Column(name = "empresa_prov")
    private String empresa;

    @Column(name = "zona_prov")
    private String zona;

}
