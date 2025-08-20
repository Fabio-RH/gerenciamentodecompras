package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.Usuario;
import com.senac.gerenciamentodecompras.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public List<Usuario> listarUsuario(){
        return this.usuarioRepository.findAll();
    }


}
