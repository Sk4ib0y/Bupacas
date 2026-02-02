package com.bupacas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Producto")
public class Producto
{
    @Id
    @Column(name = "idProducto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "costoGanancia")
    private BigDecimal costoGanancia;

    @Column(name = "empaque")
    private String empaque;

    @Column(name = "merma")
    private String merma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pedido_idPedido")
    private Pedido idPedido;
}
