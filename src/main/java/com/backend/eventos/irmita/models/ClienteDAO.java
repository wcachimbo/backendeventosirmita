package com.backend.eventos.irmita.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="cliente")
public class ClienteDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cliente_id;

    @Size(min=4, max = 20, message = "El tamaño tiene que estar entre 4 y 12")
    @Column(name  = "nombre", nullable = false, length = 20, columnDefinition = "VARCHAR(20)")
    @NotEmpty(message = "El nombre no puede ser vacio")
    private String nombre;

    @Size(min=2, max = 100, message = "El tamaño tiene que estar entre 2 y 100")
    @Column(name  = "direccion", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
    @NotEmpty(message = "La dirección  no puede ser vacio")
    private String direccion;

    @Size(min=10, max = 10, message = "El tamaño debe tener 10 numeros")
    @Column(name  = "celular", nullable = false, length = 10, columnDefinition = "VARCHAR(10)")
    @NotEmpty(message = "Debe ser un numero celular")
    private String celular;
}
