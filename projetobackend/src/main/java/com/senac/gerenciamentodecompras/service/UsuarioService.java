package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.entity.Usuario;
import com.senac.gerenciamentodecompras.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        return this.usuarioRepository.findAll();


    }
}