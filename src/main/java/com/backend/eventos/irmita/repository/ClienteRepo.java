package com.backend.eventos.irmita.repository;

import com.backend.eventos.irmita.models.ClienteDAO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("clineteRepo")
public interface ClienteRepo extends CrudRepository<ClienteDAO, Long> {

    @Query(value= "SELECT *FROM cliente WHERE celular = :celular", nativeQuery = true)
    List<ClienteDAO> getClientes(@Param("celular") String celular);


    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO cliente (cliente_id, celular, direccion, nombre)
    VALUES (:cliente_id,:celularC, :direccionC, :nombreC)
    """, nativeQuery = true)
    int insertClienteo(
            @Param("cliente_id") UUID cliente_id,
            @Param("celularC") String celularC,
            @Param("direccionC") String direccionC,
            @Param("nombreC") String nombreC);
}
