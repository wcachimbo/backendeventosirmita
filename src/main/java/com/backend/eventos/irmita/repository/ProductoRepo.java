package com.backend.eventos.irmita.repository;

import com.backend.eventos.irmita.models.ProductoDAO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;


@Repository("productoRepo")
public interface ProductoRepo extends CrudRepository<ProductoDAO, Long> {

    /***
     *
     * @param cantidad_prodcuto
     * @param nombreproducto
     * @param precio
     * @param facutra_pedido_id
     * @param pedido_id
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO producto (producto_id, cantidad_prodcuto, factura_id, nombreproducto, precio, pedido_id, fechapedido, totalproducto)
    VALUES (:producto_id, :cantidad_prodcuto, :facutra_pedido_id, :nombreproducto, :precio, :pedido_id, :fechapedido, :totalproducto)
    """, nativeQuery = true)
    int insertProducto(
            @Param("producto_id") UUID producto_id,
            @Param("cantidad_prodcuto") int cantidad_prodcuto,
            @Param("facutra_pedido_id") String facutra_pedido_id,
            @Param("nombreproducto") String nombreproducto,
            @Param("precio") double precio,
            @Param("pedido_id") UUID pedido_id,
            @Param("fechapedido") LocalDate fechapedido,
            @Param("totalproducto") double totalproducto);


    @Query(value = """
    SELECT COALESCE(SUM(cantidad_prodcuto),0) AS cantidad FROM producto
    WHERE nombreproducto = :nombreproducto
    AND fechapedido = :fecha
    """, nativeQuery = true)
    Integer disponibilidadProducto(
            @Param("nombreproducto") String nombreproducto,
            @Param("fecha") LocalDate fecha);

}
