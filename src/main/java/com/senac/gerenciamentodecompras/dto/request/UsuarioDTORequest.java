package com.senac.gerenciamentodecompras.dto.request;

public class UsuarioDTORequest {


    private String usuario_nome;

    private String usuario_email;



    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_email() {
        return usuario_email;
    }

    public void setUsuario_email(String usuario_email) {
        this.usuario_email = usuario_email;
    }

    public String getUsuario_senha() {
        return usuario_senha;
    }

    public void setUsuario_senha(String usuario_senha) {
        this.usuario_senha = usuario_senha;
    }

    public int getUsuario_status() {
        return usuario_status;
    }

    public void setUsuario_status(int usuario_status) {
        this.usuario_status = usuario_status;
    }


    private String usuario_senha;

    private int usuario_status;







}
