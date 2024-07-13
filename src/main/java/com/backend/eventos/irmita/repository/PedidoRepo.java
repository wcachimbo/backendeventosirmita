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
import java.util.UUID;


@Repository("pedidoRepo")
public interface PedidoRepo extends CrudRepository<PedidoDAO, Long> {

    List<PedidoDAO> findAll();

    @Query(value = "SELECT *FROM pedido WHERE estadop = :status AND fecha = :fecha" , nativeQuery = true)
    List<PedidoDAO> pedidoActivo(@Param("status") String status,
                                 @Param("fecha") LocalDate fecha);

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO pedido (pedido_id, celularcliente, descripcion, direccioncliente, estadop, factura, fecha, nombrecliente, total)
        VALUES (:pedido_id, :celular, :descripcion, :direccion, :status, :factura, :fecha, :nombre, :total)
    """, nativeQuery = true)
    int insertPedido(
            @Param("pedido_id") UUID pedido_id,
            @Param("celular") String celular,
            @Param("descripcion") String descripcion,
            @Param("direccion") String direccion,
            @Param("status") String status,
            @Param("factura") String factura,
            @Param("fecha") LocalDate fecha,
            @Param("nombre") String nombre,
            @Param("total") double total);
}
