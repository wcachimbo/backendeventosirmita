package com.backend.eventos.irmita.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name="producto")
public class ProductoDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID producto_id;

    @Column(name  = "factura_id", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
    private String factura;

    @Column(name  = "nombreproducto", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private String nombreProducto;

    @Column(name  = "cantidadProdcuto", nullable = false)
    @Min(value = 1, message = "El cantidadprodcuto debe ser mayor a cero")
    private int cantidadprodcuto;

    @Column(name  = "precio", nullable = false)
    @Min(value = 1, message = "El precio debe ser mayor a cero")
    private double precio;

    @Column(name  = "totalproducto", nullable = false)
    private double totalProducto;


    @Temporal(TemporalType.DATE)
    @Column(name  = "fechapedido", nullable = false, columnDefinition = "DATE")
    private String fechapedido;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoDAO pedido;

}
