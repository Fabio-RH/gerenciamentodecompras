package com.senac.gerenciamentodecompras.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="lista")
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lista_id")
    private Integer id;
    @Column(name = "lista_nome")
    private String nome;
    @Column(name = "lista_data_criacao")
    private LocalDateTime data_criacao;
    @Column(name = "lista_status")
    private int status;

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

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}