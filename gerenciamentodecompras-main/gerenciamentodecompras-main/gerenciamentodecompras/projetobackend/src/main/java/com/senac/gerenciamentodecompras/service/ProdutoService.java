package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.entity.Produto;
import com.senac.gerenciamentodecompras.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos(){
        return this.produtoRepository.findAll();


    }
}