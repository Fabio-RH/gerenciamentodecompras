package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.entity.Item;
import com.senac.gerenciamentodecompras.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    private ItemService itemService;
    @GetMapping("/listar")
    public ResponseEntity<List<Item>> listarItens(){
        return ResponseEntity.ok(itemService.listarItens());

    }

}
