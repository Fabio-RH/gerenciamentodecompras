package com.senac.gerenciamentodecompras.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="recibo")
public class Recibo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recibo_id")
    private Integer id;
    @Column(name = "recibo_caminho_imagem")
    private String caminho_imagem;
    @Column(name = "recibo_valor")
    private float valor;
    @Column(name = "recibo_observacao")
    private String observacao;
    @Column(name = "recibo_data_upload")
    private LocalDateTime data_upload;
    @Column(name = "recibo_status")
    private int status;

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
}