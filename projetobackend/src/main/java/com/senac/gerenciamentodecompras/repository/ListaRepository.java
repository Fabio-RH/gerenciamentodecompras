package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer> {
}
