package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Recibo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Recibo r SET r.recibo_status = -1 WHERE r.id = :id")
    void apagadoLogicoRecibo(@Param("id") Integer recibo);

    @Query("SELECT r from Recibo r WHERE r.recibo_status >= 0")
    List<Recibo> listarRecibos();

    @Query("SELECT r from Recibo r where r.id=:id AND r.recibo_status >=0")
    Recibo obterReciboPeloId(@Param("id") Integer reciboId);
}
