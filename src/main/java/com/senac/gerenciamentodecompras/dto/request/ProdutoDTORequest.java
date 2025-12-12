package com.senac.gerenciamentodecompras.dto.request;

import com.senac.gerenciamentodecompras.dto.response.UsuarioDTOResponse;

public class ProdutoDTORequest {
//sem id
    private String produto_nome;

    private String produto_categoria;

    private String produto_unidade_medida;

    public String getProduto_nome() {
        return produto_nome;
    }

    public void setProduto_nome(String produto_nome) {
        this.produto_nome = produto_nome;
    }

    public String getProduto_categoria() {
        return produto_categoria;
    }

    public void setProduto_categoria(String produto_categoria) {
        this.produto_categoria = produto_categoria;
    }

    public String getProduto_unidade_medida() {
        return produto_unidade_medida;
    }

    public void setProduto_unidade_medida(String produto_unidade_medida) {
        this.produto_unidade_medida = produto_unidade_medida;
    }

    public int getProduto_status() {
        return produto_status;
    }

    public void setProduto_status(int produto_status) {
        this.produto_status = produto_status;
    }

    private int produto_status;







}
