package com.senac.gerenciamentodecompras.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioDTOLoginRequest {
    @JsonProperty("usuario_email")
    private String usuarioEmail;

    private String usuario_senha;


    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuario_senha() {
        return usuario_senha;
    }

    public void setUsuario_senha(String usuario_senha) {
        this.usuario_senha = usuario_senha;
    }
}