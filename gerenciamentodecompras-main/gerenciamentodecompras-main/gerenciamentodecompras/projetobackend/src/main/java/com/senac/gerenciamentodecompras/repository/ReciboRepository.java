package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Integer> {
}