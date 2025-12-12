package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UsuarioDetailsImpl implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Garantindo que o prefixo ROLE_ apareça apenas uma vez
        return usuario.getRoles()
                .stream()
                .map(role -> role.getName().name()) // ex: "ROLE_LIDERCOMITE"
                .map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuario.getUsuario_senha();
    }

    @Override
    public String getUsername() {
        return usuario.getUsuarioEmail();
    }

    public Integer getIdUsuario() {
        return usuario.getUsuario_id();
    }

    public String getNomeUsuario() {
        return usuario.getUsuario_nome();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getUsuario_status() == 1;
    }
}
