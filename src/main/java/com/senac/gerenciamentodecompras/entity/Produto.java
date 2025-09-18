package com.senac.gerenciamentodecompras.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Integer produto_id;
    @Column(name = "produto_nome")
    private String produto_nome;
    @Column(name = "produto_categoria")
    private String produto_categoria;
    @Column(name = "produto_unidade_medida")
    private String produto_unidade_medida;
    @Column(name = "produto_status")
    private int produto_status;

    @OneToMany (mappedBy = "item_id")
    private Set<Item> itens;

    public Integer getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Integer produto_id) {
        this.produto_id = produto_id;
    }

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

    public Set<Item> getItens() {
        return itens;
    }

    public void setItens(Set<Item> itens) {
        this.itens = itens;
    }
}