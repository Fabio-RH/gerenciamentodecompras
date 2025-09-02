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
    private Integer id;
    @Column(name = "produto_nome")
    private String nome;
    @Column(name = "produto_categoria")
    private String categoria;
    @Column(name = "produto_unidade_medida")
    private String unidade_medida;
    @Column(name = "produto_status")
    private int status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private  Usuario usuario;
    @OneToMany (mappedBy = "produto")
    private Set<Item> itens;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidade_medida() {
        return unidade_medida;
    }

    public void setUnidade_medida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Item> getItens() {
        return itens;
    }

    public void setItens(Set<Item> itens) {
        this.itens = itens;
    }
}