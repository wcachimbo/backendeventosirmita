package com.backend.eventos.irmita.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name="stock")
public class StockDAO {

    @Id
    private UUID id_stock;

    @Column(name  = "nombrepd", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    private String nombrePD;

    @Column(name  = "cantidadpd", nullable = false)
    private int cantidadPD;

    @Column(name  = "inventario", nullable = false)
    private int inventario;

}
