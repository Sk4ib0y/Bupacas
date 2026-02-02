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
@Table(name = "Banco_has_Pedido")
public class Banco_has_Pedido {

    @Id
    @Column(name = "idBanco_has_Pedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @Column(name = "Banco_idBanco")
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Banco_idBanco")
    private Banco idBanco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pedido_idPedido")
    private Pedido idPedido;
}