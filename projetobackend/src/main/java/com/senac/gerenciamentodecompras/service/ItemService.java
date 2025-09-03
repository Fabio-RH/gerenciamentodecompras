package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.entity.Item;
import com.senac.gerenciamentodecompras.repository.ItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> listarItens(){
        return this.itemRepository.findAll();


    }
}