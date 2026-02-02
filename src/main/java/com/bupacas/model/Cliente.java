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
@Table(name = "Cliente")
public class Cliente {

    @Id
    @Column(name = "idCliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "RFC_clte")
    private String rfc;

    @Column(name = "nombre_clte")
    private String nombre;

    @Column(name = "empresa_clte")
    private String empresa;

    @Column(name = "zona_clte   ")
    private String zona;

}
