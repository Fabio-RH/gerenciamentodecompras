package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.dto.request.ItemDTORequest;
import com.senac.gerenciamentodecompras.dto.response.ItemDTOResponse;
import com.senac.gerenciamentodecompras.dto.response.ItemDTOResponseP;
import com.senac.gerenciamentodecompras.dto.response.ItemDTOUpdateResponse;
import com.senac.gerenciamentodecompras.entity.Item;
import com.senac.gerenciamentodecompras.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/listar")
    @Operation(
            summary = "Listar itens",
            description = "Endpoint para listar todos os itens"
    )
    public ResponseEntity<List<ItemDTOResponse>> listarItens() {
        return ResponseEntity.ok(itemService.listarItens());
    }


    @GetMapping("/listar/{listaId}")
    public ResponseEntity<List<ItemDTOResponseP>> listarItensPorListaId(@PathVariable("listaId") Integer listaId){
        return ResponseEntity.ok(itemService.listarItensPorListaId(listaId));
    }

    @GetMapping("/listarPorItemId/{itemId}")
    @Operation(
            summary = "Listar item pelo id de item",
            description = "Endpoint para listar item por Id de item"
    )
    public ResponseEntity<ItemDTOResponse> listarPorItemId(@PathVariable("itemId") Integer itemId) {
        ItemDTOResponse item = itemService.listarPorItemId(itemId);
        if (item == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(item);
        }
    }



    @PostMapping("/criar")
    @Operation(
            summary = "Criar novo item",
            description = "Endpoint para criar um novo registro de item"
    )
    public ResponseEntity<ItemDTOResponse> criarItem(
            @Valid @RequestBody ItemDTORequest item
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.criarItem(item));
    }

    @PutMapping("/atualizar/{itemId}")
    @Operation(
            summary = "Atualizar todos os dados do item",
            description = "Endpoint para atualizar o registro de item"
    )
    public ResponseEntity<ItemDTOResponse> atualizarItem(
            @PathVariable("itemId") Integer itemId,
            @Valid @RequestBody ItemDTORequest itemDTORequest
    ) {
        return ResponseEntity.ok(itemService.atualizarItem(itemId, itemDTORequest));
    }

    @PatchMapping("/atualizarStatus/{itemId}")
    @Operation(
            summary = "Atualizar campo status do item",
            description = "Endpoint para atualizar apenas o status do item"
    )
    public ResponseEntity<ItemDTOUpdateResponse> atualizarStatusItem(
            @PathVariable("itemId") Integer itemId,
            @Valid @RequestBody ItemDTORequest itemDTOUpdateRequest
    ) {
        return ResponseEntity.ok(itemService.atualizarStatusItem(itemId, itemDTOUpdateRequest));
    }



    @DeleteMapping("/apagar/{itemId}")
    @Operation(
            summary = "Apagar registro do item",
            description = "Endpoint para apagar registro do item"
    )
    public ResponseEntity<Void> apagarItem(@PathVariable("itemId") Integer itemId) {
        itemService.apagarItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
