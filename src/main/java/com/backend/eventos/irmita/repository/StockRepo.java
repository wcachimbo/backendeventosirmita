package com.backend.eventos.irmita.repository;

import com.backend.eventos.irmita.models.StockDAO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StockRepo extends CrudRepository<StockDAO, Long> {

    @Query(value = """
    SELECT COALESCE(cantidadpd ,0) AS cantidadpd FROM stock WHERE nombrepd = :nombrepd
    """, nativeQuery = true)
    Integer stockProduct(
            @Param("nombrepd") String nombrepd);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE stock SET cantidadpd = IFNULL(cantidadpd, 0) - :cantidad WHERE nombrepd = :nombrep
        """, nativeQuery = true)
        Integer updateStock(
            @Param("cantidad") int cantidad,
            @Param("nombrep") String nombrep);

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO stock(fecha,nombrepd,cantidadpd) SET cantidadpd = IFNULL(cantidadpd, 0) - :cantidad WHERE nombrepd = :nombrep
        """, nativeQuery = true)
    Integer insertStock(
            @Param("cantidad") int cantidad,
            @Param("nombrep") String nombrep);

}
