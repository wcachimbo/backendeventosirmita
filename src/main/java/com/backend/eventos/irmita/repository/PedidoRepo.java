package com.backend.eventos.irmita.repository;

import com.backend.eventos.irmita.models.PedidoDAO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository("pedidoRepo")
public interface PedidoRepo extends CrudRepository<PedidoDAO, Long> {

    List<PedidoDAO> findAll();

    @Query(value = "SELECT COALESCE(MAX(pedido_id), 0) AS ultimopedido FROM pedido" , nativeQuery = true)
    int pedidoID();

    @Query(value = "SELECT *FROM pedido WHERE estadop = :status AND fecha = :fecha" , nativeQuery = true)
    List<PedidoDAO> pedidoActivo(@Param("status") String status,
                                 @Param("fecha") LocalDate fecha);

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO pedido (celularcliente, descripcion, direccioncliente, estadop, factura, fecha, nombrecliente, total)
        VALUES (:celular, :descripcion, :direccion, :status, :factura, :fecha, :nombre, :total)
    """, nativeQuery = true)
    int insertPedido(
            @Param("celular") String celular,
            @Param("descripcion") String descripcion,
            @Param("direccion") String direccion,
            @Param("status") String status,
            @Param("factura") String factura,
            @Param("fecha") LocalDate fecha,
            @Param("nombre") String nombre,
            @Param("total") double total);
}
