package com.senac.gerenciamentodecompras.repository;

import com.senac.gerenciamentodecompras.entity.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Item i SET i.item_status = -1 WHERE i.id = :id")
    void apagadoLogicoItem(@Param("id") Integer item);

    @Query("SELECT i from Item i WHERE i.item_status >= 0")
    List<Item> listarItens();

    @Query("SELECT i from Item i where i.id=:id AND i.item_status >=0")
    Item obterItemPeloId(@Param("id") Integer itemId);
}
