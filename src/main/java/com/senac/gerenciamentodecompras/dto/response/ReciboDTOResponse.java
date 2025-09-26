package com.senac.gerenciamentodecompras.dto.response;

import java.time.LocalDateTime;

public class ReciboDTOResponse {

    private Integer recibo_id;

    private String recibo_caminho_imagem;

    private float recibo_valor;

    private String recibo_observacao;

    private LocalDateTime recibo_data_upload;

    private int recibo_status;

    private Integer lista_id;


    public Integer getRecibo_id() {
        return recibo_id;
    }

    public void setRecibo_id(Integer recibo_id) {
        this.recibo_id = recibo_id;
    }

    public String getRecibo_caminho_imagem() {
        return recibo_caminho_imagem;
    }

    public void setRecibo_caminho_imagem(String recibo_caminho_imagem) {
        this.recibo_caminho_imagem = recibo_caminho_imagem;
    }

    public float getRecibo_valor() {
        return recibo_valor;
    }

    public void setRecibo_valor(float recibo_valor) {
        this.recibo_valor = recibo_valor;
    }

    public String getRecibo_observacao() {
        return recibo_observacao;
    }

    public void setRecibo_observacao(String recibo_observacao) {
        this.recibo_observacao = recibo_observacao;
    }

    public LocalDateTime getRecibo_data_upload() {
        return recibo_data_upload;
    }

    public void setRecibo_data_upload(LocalDateTime recibo_data_upload) {
        this.recibo_data_upload = recibo_data_upload;
    }

    public int getRecibo_status() {
        return recibo_status;
    }

    public void setRecibo_status(int recibo_status) {
        this.recibo_status = recibo_status;
    }

    public Integer getLista_id() {
        return lista_id;
    }

    public void setLista_id(Integer lista_id) {
        this.lista_id = lista_id;
    }
}
