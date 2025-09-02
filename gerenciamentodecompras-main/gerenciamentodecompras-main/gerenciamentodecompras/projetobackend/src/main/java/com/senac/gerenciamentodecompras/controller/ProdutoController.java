package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.entity.Produto;
import com.senac.gerenciamentodecompras.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    private ProdutoService produtoService;
    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutos(){
        return ResponseEntity.ok(produtoService.listarProdutos());

    }

}
