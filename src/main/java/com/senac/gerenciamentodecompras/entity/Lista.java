package com.senac.gerenciamentodecompras.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="lista")
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lista_id")
    private Integer lista_id;


    @Column(name = "lista_nome")
    private String lista_nome;
    @Column(name = "lista_data_criacao")
    private LocalDateTime lista_dataCriacao;


    @Column(name = "lista_status")
    private int lista_status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "lista")
    private Set<Item> itens;

    @OneToMany(mappedBy = "lista")
    private Set<Recibo> recibos;

    public Integer getLista_id() {
        return lista_id;
    }

    public void setLista_id(Integer lista_id) {
        this.lista_id = lista_id;
    }

    public String getLista_nome() {
        return lista_nome;
    }

    public void setLista_nome(String lista_nome) {
        this.lista_nome = lista_nome;
    }

    public LocalDateTime getLista_dataCriacao() {
        return lista_dataCriacao;
    }

    public void setLista_dataCriacao(LocalDateTime lista_dataCriacao) {
        this.lista_dataCriacao = lista_dataCriacao;
    }

    public int getLista_status() {
        return lista_status;
    }

    public void setLista_status(int lista_status) {
        this.lista_status = lista_status;
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

    public Set<Recibo> getRecibos() {
        return recibos;
    }

    public void setRecibos(Set<Recibo> recibos) {
        this.recibos = recibos;
    }
}