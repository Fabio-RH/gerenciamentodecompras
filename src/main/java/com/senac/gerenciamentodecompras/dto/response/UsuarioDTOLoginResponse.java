package com.senac.gerenciamentodecompras.dto.response;

public class UsuarioDTOLoginResponse {

    private Integer usuario_id;
    private String usuario_nome;
    private String usuario_token;

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

    public String getUsuario_token() {
        return usuario_token;
    }

    public void setUsuario_token(String usuario_token) {
        this.usuario_token = usuario_token;
    }
}
