package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Produto p SET p.produto_status = -1 WHERE p.id = :id")
    void apagadoLogicoProduto(@Param("id") Integer produto);

    @Query("SELECT p from Produto p WHERE p.produto_status >= 0")
    List<Produto> listarProdutos();

    @Query("SELECT p from Produto p where p.id=:id AND p.produto_status >=0")
    Produto obterProdutoPeloId(@Param("id") Integer produtoId);
}
