package com.senac.gerenciamentodecompras.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="recibo")
public class Recibo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recibo_id")
    private Integer recibo_id;
    @Column(name = "recibo_caminho_imagem")
    private String recibo_caminho_imagem;
    @Column(name = "recibo_valor")
    private float recibo_valor;
    @Column(name = "recibo_observacao")
    private String recibo_observacao;
    @Column(name = "recibo_data_upload")
    private LocalDateTime recibo_data_upload;
    @Column(name = "recibo_status")
    private int recibo_status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lista_id", nullable = false)
    private  Lista lista;

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

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }
}