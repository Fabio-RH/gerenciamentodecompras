package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
