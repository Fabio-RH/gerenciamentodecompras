package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.config.SecurityConfiguration;
import com.senac.gerenciamentodecompras.dto.request.UsuarioDTOLoginRequest;
import com.senac.gerenciamentodecompras.dto.request.UsuarioDTORequest;
import com.senac.gerenciamentodecompras.dto.response.*;
import com.senac.gerenciamentodecompras.entity.Role;
import com.senac.gerenciamentodecompras.entity.Usuario;
import com.senac.gerenciamentodecompras.repository.RoleRepository;
import com.senac.gerenciamentodecompras.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final SecurityConfiguration securityConfiguration;
    private final JwtTokenService jwtTokenService;

    public UsuarioService(AuthenticationManager authenticationManager,
                          UsuarioRepository usuarioRepository,
                          RoleRepository roleRepository,
                          ModelMapper modelMapper,
                          SecurityConfiguration securityConfiguration,
                          JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.securityConfiguration = securityConfiguration;
        this.jwtTokenService = jwtTokenService;
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

        // 🔒 Criptografar senha antes de salvar
        usuario.setUsuario_senha(securityConfiguration.passwordEncoder().encode(usuario.getUsuario_senha()));

        // 🔑 Atribuir roles pelo ID
        if (usuarioDTORequest.getRoleList() != null) {
            List<Role> roles = usuarioDTORequest.getRoleList().stream()
                    .map(roleId -> roleRepository.findById(roleId)
                            .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleId)))
                    .collect(Collectors.toList());
            usuario.setRoles(roles);
        }

        Usuario usuarioSave = usuarioRepository.save(usuario);
        return modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
    }

    @Transactional
    public UsuarioDTOResponse atualizarUsuario(Integer usuarioId, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);
        if (usuario != null) {
            modelMapper.map(usuarioDTORequest, usuario);
            if (usuarioDTORequest.getUsuario_senha() != null) {
                usuario.setUsuario_senha(securityConfiguration.passwordEncoder().encode(usuarioDTORequest.getUsuario_senha()));
            }
            // Atualizar roles se fornecido
            if (usuarioDTORequest.getRoleList() != null) {
                List<Role> roles = usuarioDTORequest.getRoleList().stream()
                        .map(roleId -> roleRepository.findById(roleId)
                                .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleId)))
                        .collect(Collectors.toList());
                usuario.setRoles(roles);
            }
            Usuario tempResponse = usuarioRepository.save(usuario);
            return modelMapper.map(tempResponse, UsuarioDTOResponse.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public UsuarioDTOUpdateResponse atualizarStatusUsuario(Integer usuarioId, UsuarioDTORequest usuarioDTOUpdateRequest) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);
        if (usuario != null) {
            usuario.setUsuario_status(usuarioDTOUpdateRequest.getUsuario_status());
            Usuario usuarioSave = usuarioRepository.save(usuario);
            return modelMapper.map(usuarioSave, UsuarioDTOUpdateResponse.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void apagarUsuario(Integer usuarioId) {
        usuarioRepository.apagadoLogicoUsuario(usuarioId);
    }

    // ================= LOGIN COM JWT =================
    public UsuarioDTOLoginResponse login(UsuarioDTOLoginRequest usuarioDTOLoginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioDTOLoginRequest.getUsuarioEmail(),
                            usuarioDTOLoginRequest.getUsuario_senha()
                    )
            );

            UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

            String token = jwtTokenService.generateToken(userDetails);

            UsuarioDTOLoginResponse response = new UsuarioDTOLoginResponse();
            response.setUsuario_id(userDetails.getIdUsuario());
            response.setUsuario_nome(userDetails.getNomeUsuario());
            response.setUsuario_token(token);
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Falha na autenticação: " + e.getMessage());
        }
    }
}
