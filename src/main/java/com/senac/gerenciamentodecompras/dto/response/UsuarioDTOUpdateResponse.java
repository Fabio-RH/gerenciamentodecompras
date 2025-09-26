package com.senac.gerenciamentodecompras.dto.response;

public class UsuarioDTOUpdateResponse {

    private Integer usuario_id;
    private int usuario_status;

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getUsuario_status() {
        return usuario_status;
    }

    public void setUsuario_status(int usuario_status) {
        this.usuario_status = usuario_status;
    }
}
