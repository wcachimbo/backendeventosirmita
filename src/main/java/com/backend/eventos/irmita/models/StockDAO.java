package com.backend.eventos.irmita.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name="stock")
public class StockDAO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id_stock;

    @Temporal(TemporalType.DATE)
    @Column(name  = "fecha", nullable = false, columnDefinition = "DATE")
    private String fecha;

    @Column(name  = "nombrepd", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private String nombrePD;

    @Column(name  = "cantidadpd", nullable = false)
    private int cantidadPD;

    @Column(name  = "inventario", nullable = false)
    private int inventario;

}
