package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.usuario_status = -1 WHERE u.id = :id")
    void apagadoLogicoUsuario(@Param("id") Integer usuario);

    @Query("SELECT u from Usuario u WHERE u.usuario_status >= 0")
    List<Usuario> listarUsuarios();

    @Query("SELECT u from Usuario u where u.id=:id AND u.usuario_status >=0")
    Usuario obterUsuarioPeloId(@Param("id") Integer usuarioId);

    Optional<Usuario> findByUsuarioEmail(String usuarioEmail);
}
