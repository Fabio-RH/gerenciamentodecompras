package com.senac.gerenciamentodecompras.dto.response;

public class ProdutoDTOResponse {

    private Integer produto_id;

    private String produto_nome;

    private String produto_categoria;

    private String produto_unidade_medida;

    private int produto_status;


    private int produto_itemId;



    public String getProduto_nome() {
        return produto_nome;
    }

    public void setProduto_nome(String produto_nome) {
        this.produto_nome = produto_nome;
    }

    public Integer getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Integer produto_id) {
        this.produto_id = produto_id;
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

    public int getProduto_itemId() {
        return produto_itemId;
    }

    public void setProduto_itemId(int produto_itemId) {
        this.produto_itemId = produto_itemId;
    }
}
