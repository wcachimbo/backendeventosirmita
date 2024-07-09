package com.backend.eventos.irmita.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="pedido")
public class PedidoDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedido_id;

    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(name = "pedido_id", referencedColumnName = "pedido_id"))
    private List<ProductoDAO> productop = new ArrayList<>();

    @Column(name  = "factura", nullable = false, length = 10, columnDefinition = "VARCHAR(10)")
    private String factura;

    @Size(min=4, max = 20, message = "El tamaño tiene que estar entre 4 y12")
    @Column(name  = "nombrecliente", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    @NotEmpty(message = "El nombre no puede ser vacio")
    private String nombreCliente;

    @Temporal(TemporalType.DATE)
    @Column(name  = "fecha", nullable = false, columnDefinition = "DATE")
    @NotEmpty(message = "la fecha no puede ser vacia")
    private String fecha;

    @Column(name  = "total", nullable = false)
    @Min(value = 0, message = "El total debe ser mayor a cero")
    private double total;

    @Size(min=10, max = 10, message = "El tamaño debe tener 10 numeros")
    @Column(name  = "celularcliente", nullable = false, length = 10, columnDefinition = "VARCHAR(10)")
    @NotEmpty(message = "Debe ser un numero celular")
    private String celularCliente;

    @Size(min=2, max = 100, message = "El tamaño tiene que estar entre 2 y 100")
    @Column(name  = "direccioncliente", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
    @NotEmpty(message = "La dirección  no puede ser vacio")
    private String direccionCliente;

    @Column(name  = "descripcion", nullable = true, length = 200, columnDefinition = "VARCHAR(200)")
    private String descripcion;

    @Column(name  = "estadop", nullable = false, length = 3, columnDefinition = "VARCHAR(3)")
    private String estadoP;


}
