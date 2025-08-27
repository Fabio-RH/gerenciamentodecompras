package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}