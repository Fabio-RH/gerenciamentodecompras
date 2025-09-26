package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.dto.request.ProdutoDTORequest;
import com.senac.gerenciamentodecompras.dto.response.ProdutoDTOResponse;
import com.senac.gerenciamentodecompras.dto.response.ProdutoDTOUpdateResponse;
import com.senac.gerenciamentodecompras.entity.Produto;
import com.senac.gerenciamentodecompras.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/listar")
    @Operation(
            summary = "Listar produtos",
            description = "Endpoint para listar todos os produtos"
    )
    public ResponseEntity<List<ProdutoDTOResponse>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/listarPorProdutoId/{produtoId}")
    @Operation(
            summary = "Listar produto pelo id de produto",
            description = "Endpoint para listar produto por Id de produto"
    )
    public ResponseEntity<ProdutoDTOResponse> listarPorProdutoId(@PathVariable("produtoId") Integer produtoId) {
        ProdutoDTOResponse produto = produtoService.listarPorProdutoId(produtoId);
        if (produto == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(produto);
        }
    }

    @PostMapping("/criar")
    @Operation(
            summary = "Criar novo produto",
            description = "Endpoint para criar um novo registro de produto"
    )
    public ResponseEntity<ProdutoDTOResponse> criarProduto(
            @Valid @RequestBody ProdutoDTORequest produto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criarProduto(produto));
    }

    @PutMapping("/atualizar/{produtoId}")
    @Operation(
            summary = "Atualizar todos os dados do produto",
            description = "Endpoint para atualizar o registro do produto"
    )
    public ResponseEntity<ProdutoDTOResponse> atualizarProduto(
            @PathVariable("produtoId") Integer produtoId,
            @Valid @RequestBody ProdutoDTORequest produtoDTORequest
    ) {
        return ResponseEntity.ok(produtoService.atualizarProduto(produtoId, produtoDTORequest));
    }

    @PatchMapping("/atualizarStatus/{produtoId}")
    @Operation(
            summary = "Atualizar campo status do produto",
            description = "Endpoint para atualizar apenas o status do produto"
    )
    public ResponseEntity<ProdutoDTOUpdateResponse> atualizarStatusProduto(
            @PathVariable("produtoId") Integer produtoId,
            @Valid @RequestBody ProdutoDTORequest produtoDTOUpdateRequest
    ) {
        return ResponseEntity.ok(produtoService.atualizarStatusProduto(produtoId, produtoDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{produtoId}")
    @Operation(
            summary = "Apagar registro do produto",
            description = "Endpoint para apagar registro do produto"
    )
    public ResponseEntity<Void> apagarProduto(@PathVariable("produtoId") Integer produtoId) {
        produtoService.apagarProduto(produtoId);
        return ResponseEntity.noContent().build();
    }
}
