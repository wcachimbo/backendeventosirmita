package com.backend.eventos.irmita.repository;

import com.backend.eventos.irmita.models.ClienteDAO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("clineteRepo")
public interface ClienteRepo extends CrudRepository<ClienteDAO, Long> {

    @Query(value= "SELECT *FROM PEDIDO WHERE celular = :celular", nativeQuery = true)
    List<ClienteDAO> getClientes(@Param("celular") String celular);


    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO pedido (celularcliente, descripcion, direccioncliente, fecha, nombrecliente, estadop)
    VALUES (:celular, :descripcion, :direccion, :fecha, :nombre, :status)
    """, nativeQuery = true)
    int insertPedido(
            @Param("celular") String celular,
            @Param("descripcion") String descripcion,
            @Param("direccion") String direccion,
            @Param("fecha") Date fecha,
            @Param("nombre") String nombre,
            @Param("status") String status);
}
