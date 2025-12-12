package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.dto.request.ProdutoDTORequest;
import com.senac.gerenciamentodecompras.dto.response.*;
import com.senac.gerenciamentodecompras.entity.Produto;
import com.senac.gerenciamentodecompras.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public ProdutoService(ProdutoRepository produtoRepository,
                          ModelMapper modelMapper) {
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProdutoDTOResponse> listarProdutos() {
        return produtoRepository.listarProdutos()
                .stream()
                .map(produto -> modelMapper.map(produto, ProdutoDTOResponse.class))
                .toList();
    }

    public ProdutoDTOResponse listarPorProdutoId(Integer produtoId) {
        Produto produto = produtoRepository.obterProdutoPeloId(produtoId);
        return (produto != null) ? modelMapper.map(produto, ProdutoDTOResponse.class) : null;
    }

    @Transactional
    public ProdutoDTOResponse criarProduto(ProdutoDTORequest produtoDTORequest) {
        Produto produto = modelMapper.map(produtoDTORequest, Produto.class);
        Produto produtoSave = this.produtoRepository.save(produto);
        return modelMapper.map(produtoSave, ProdutoDTOResponse.class);
    }

    @Transactional
    public ProdutoDTOResponse atualizarProduto(Integer produtoId, ProdutoDTORequest produtoDTORequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Produto produto = produtoRepository.obterProdutoPeloId(produtoId);
        // se encontra o registro a ser atualizado
        if (produto != null) {
            // atualiza dados do produto a partir do DTO
            modelMapper.map(produtoDTORequest, produto);
            // atualiza o produto vinculado
            Produto tempResponse = produtoRepository.save(produto);
            return modelMapper.map(tempResponse, ProdutoDTOResponse.class);
        } else {
            // Error 400 caso tente atualizar produto inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ProdutoDTOUpdateResponse atualizarStatusProduto(Integer produtoId, ProdutoDTORequest produtoDTOUpdateRequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Produto produto = produtoRepository.obterProdutoPeloId(produtoId);
        // se encontra o registro a ser atualizado
        if (produto != null) {
            // atualiza o status do produto a partir do DTO
            produto.setProduto_status(produtoDTOUpdateRequest.getProduto_status());
            Produto produtoSave = produtoRepository.save(produto);
            return modelMapper.map(produtoSave, ProdutoDTOUpdateResponse.class);
        } else {
            // Error 400 caso tente atualizar produto inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void apagarProduto(Integer produtoId) {
        produtoRepository.apagadoLogicoProduto(produtoId);
    }
}
