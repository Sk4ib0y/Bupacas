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
@Table(name = "Papa")
public class Papa
{
    @Id
    @Column(name = "idPapa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "tamaño")
    private String tamaño;

    @Column(name = "variedad")
    private String variedad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Producto_idproducto")
    private Producto idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Proveedor_idProveedor")
    private Proveedor idProveedor;
}
