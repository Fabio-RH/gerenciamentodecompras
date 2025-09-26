package com.senac.gerenciamentodecompras.dto.response;

public class ItemDTOResponse {

    private Integer item_id;

    private int item_quantidade;

    private int item_status;

    private Integer lista_id;

    private Integer produto_id;


    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public int getItem_quantidade() {
        return item_quantidade;
    }

    public void setItem_quantidade(int item_quantidade) {
        this.item_quantidade = item_quantidade;
    }

    public int getItem_status() {
        return item_status;
    }

    public void setItem_status(int item_status) {
        this.item_status = item_status;
    }

    public Integer getLista_id() {
        return lista_id;
    }

    public void setLista_id(Integer lista_id) {
        this.lista_id = lista_id;
    }

    public Integer getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(Integer produto_id) {
        this.produto_id = produto_id;
    }
}
