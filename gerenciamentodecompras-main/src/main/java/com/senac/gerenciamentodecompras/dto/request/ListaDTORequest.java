package com.senac.gerenciamentodecompras.dto.request;

import com.senac.gerenciamentodecompras.dto.response.UsuarioDTOResponse;

import java.time.LocalDateTime;

public class ListaDTORequest {

    private String lista_nome;

    private LocalDateTime lista_dataCriacao;

    private Integer  lista_status;

    private Integer  usuario_id;



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

    public Integer getLista_status() {
        return lista_status;
    }

    public void setLista_status(Integer lista_status) {
        this.lista_status = lista_status;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }
}
