package com.senac.gerenciamentodecompras.dto.request;

public class ListaDTOUpdateRequest {

    private Integer lista_id;

    public Integer getLista_id() {
        return lista_id;
    }

    public void setLista_id(Integer lista_id) {
        this.lista_id = lista_id;
    }

    public int getLista_status() {
        return lista_status;
    }

    public void setLista_status(int lista_status) {
        this.lista_status = lista_status;
    }

    private int lista_status;
}
