package com.backend.eventos.irmita.models;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class ProductoDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name  = "nombreproducto", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private String nombreProducto;

    @Column(name  = "cantidadProdcuto", nullable = false)
    private int cantidadprodcuto;

}
