package com.backend.eventos.irmita.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class ProductoDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long producto_id;

    @Column(name  = "factura_id", nullable = false, length = 10, columnDefinition = "VARCHAR(10)")
    private String factura;

    @Column(name  = "nombreproducto", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private String nombreProducto;

    @Column(name  = "cantidadProdcuto", nullable = false)
    @Min(value = 1, message = "El cantidadprodcuto debe ser mayor a cero")
    private int cantidadprodcuto;

    @Column(name  = "precio", nullable = false)
    @Min(value = 1, message = "El precio debe ser mayor a cero")
    private double precio;

    @Temporal(TemporalType.DATE)
    @Column(name  = "fechapedido", nullable = false, columnDefinition = "DATE")
    private String fechapedido;

    @ManyToOne
    @JoinColumn(name="pedido_id")
    private PedidoDAO pedido;


}
