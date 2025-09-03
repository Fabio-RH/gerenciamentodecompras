package com.senac.gerenciamentodecompras.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;
    @Column(name = "usuario_nome")
    private String nome;
    @Column(name = "usuario_email")
    private String email;
    @Column(name="usuario_senha")
    private String senha;
    @Column(name="usuario_status")
    private int status;
    @OneToMany(mappedBy = "usuario")
    private Set<Lista> listas;
    @OneToMany(mappedBy = "usuario")
    private Set<Produto> produtos;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
