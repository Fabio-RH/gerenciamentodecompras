package com.senac.gerenciamentodecompras.dto.response;

import com.senac.gerenciamentodecompras.entity.Usuario;

import java.time.LocalDateTime;

public class ListaDTOResponse {

    private Integer lista_id;

    private String lista_nome;

    private LocalDateTime lista_data;

    private int lista_status;

//    private Usuario usuario;


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

    public LocalDateTime getLista_data() {
        return lista_data;
    }

    public void setLista_data(LocalDateTime lista_data) {
        this.lista_data = lista_data;
    }

    public int getLista_status() {
        return lista_status;
    }

    public void setLista_status(int lista_status) {
        this.lista_status = lista_status;
    }
}
