package com.senac.gerenciamentodecompras.dto.response;

public class ItemDTOResponseP {

    private Integer item_id;
    private int item_quantidade;
    private int item_status;

    // Novo campo para exibir o nome do produto
    private String item_nome;

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

    // --- Getters e Setters do novo campo ---
    public String getItem_nome() {
        return item_nome;
    }

    public void setItem_nome(String item_nome) {
        this.item_nome = item_nome;
    }
}