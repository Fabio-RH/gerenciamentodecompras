package com.senac.gerenciamentodecompras.dto.response;

import java.time.LocalDateTime;

public class ReciboDTOResponse {

    private Integer id;

    private String caminho_imagem;

    private float valor;

    private String observacao;

    private LocalDateTime data_upload;

    private int status;

    private ListaDTOResponse lista;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaminho_imagem() {
        return caminho_imagem;
    }

    public void setCaminho_imagem(String caminho_imagem) {
        this.caminho_imagem = caminho_imagem;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getData_upload() {
        return data_upload;
    }

    public void setData_upload(LocalDateTime data_upload) {
        this.data_upload = data_upload;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ListaDTOResponse getLista() {
        return lista;
    }

    public void setLista(ListaDTOResponse lista) {
        this.lista = lista;
    }
}
