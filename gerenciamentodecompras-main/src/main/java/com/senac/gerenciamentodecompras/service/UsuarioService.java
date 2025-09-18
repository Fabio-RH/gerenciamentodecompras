package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.dto.request.UsuarioDTORequest;
import com.senac.gerenciamentodecompras.dto.response.*;
import com.senac.gerenciamentodecompras.entity.Usuario;
import com.senac.gerenciamentodecompras.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<UsuarioDTOResponse> listarUsuarios() {
        return usuarioRepository.listarUsuarios()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .toList();
    }

    public UsuarioDTOResponse listarPorUsuarioId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);
        return (usuario != null) ? modelMapper.map(usuario, UsuarioDTOResponse.class) : null;
    }

    @Transactional
    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);
        Usuario usuarioSave = this.usuarioRepository.save(usuario);
        return modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
    }

    @Transactional
    public UsuarioDTOResponse atualizarUsuario(Integer usuarioId, UsuarioDTORequest usuarioDTORequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);
        // se encontra o registro a ser atualizado
        if (usuario != null) {
            // atualiza dados do usuario a partir do DTO
            modelMapper.map(usuarioDTORequest, usuario);
            // atualiza o usuario vinculado
            Usuario tempResponse = usuarioRepository.save(usuario);
            return modelMapper.map(tempResponse, UsuarioDTOResponse.class);
        } else {
            // Error 400 caso tente atualizar usuario inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public UsuarioDTOUpdateResponse atualizarStatusUsuario(Integer usuarioId, UsuarioDTORequest usuarioDTOUpdateRequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);
        // se encontra o registro a ser atualizado
        if (usuario != null) {
            // atualiza o status do usuario a partir do DTO
            usuario.setUsuario_status(usuarioDTOUpdateRequest.getUsuario_status());
            Usuario usuarioSave = usuarioRepository.save(usuario);
            return modelMapper.map(usuarioSave, UsuarioDTOUpdateResponse.class);
        } else {
            // Error 400 caso tente atualizar usuario inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void apagarUsuario(Integer usuarioId) {
        usuarioRepository.apagadoLogicoUsuario(usuarioId);
    }
}
