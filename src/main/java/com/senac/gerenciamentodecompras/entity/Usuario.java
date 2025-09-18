package com.senac.gerenciamentodecompras.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuario_id;
    @Column(name = "usuario_nome")
    private String usuario_nome;
    @Column(name = "usuario_email")
    private String usuario_email;
    @Column(name="usuario_senha")
    private String usuario_senha;
    @Column(name="usuario_status")
    private int usuario_status;
    @OneToMany(mappedBy = "lista_id")
    private Set<Lista> listas;

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_email() {
        return usuario_email;
    }

    public void setUsuario_email(String usuario_email) {
        this.usuario_email = usuario_email;
    }

    public String getUsuario_senha() {
        return usuario_senha;
    }

    public void setUsuario_senha(String usuario_senha) {
        this.usuario_senha = usuario_senha;
    }

    public int getUsuario_status() {
        return usuario_status;
    }

    public void setUsuario_status(int usuario_status) {
        this.usuario_status = usuario_status;
    }

    public Set<Lista> getListas() {
        return listas;
    }

    public void setListas(Set<Lista> listas) {
        this.listas = listas;
    }
}
