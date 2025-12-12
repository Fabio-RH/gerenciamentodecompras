package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.dto.response.ListaDTOResponse;
import com.senac.gerenciamentodecompras.entity.Lista;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Lista l SET l.lista_status = -1 WHERE l.lista_id = :id")
    void apagadoLogicoLista(@Param("id") Integer id);

    @Query("SELECT l FROM Lista l WHERE l.lista_status >= 0")
    List<Lista> listarListas();

    @Query("SELECT l FROM Lista l WHERE l.lista_id = :id AND l.lista_status >= 0")
    Lista obterListaPeloId(@Param("id") Integer listaId);

    @Query("SELECT l FROM Lista l WHERE l.usuario.usuario_id = :usuario_id")
    List<Lista> obterListaPorUsuarioId(@Param("usuario_id") Integer usuarioId);
}
