package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.dto.request.UsuarioDTOLoginRequest;
import com.senac.gerenciamentodecompras.dto.request.UsuarioDTORequest;
import com.senac.gerenciamentodecompras.dto.response.UsuarioDTOLoginResponse;
import com.senac.gerenciamentodecompras.dto.response.UsuarioDTOResponse;
import com.senac.gerenciamentodecompras.dto.response.UsuarioDTOUpdateResponse;
import com.senac.gerenciamentodecompras.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('LIDERCOMITE')") // só líderes podem listar todos
    @Operation(
            summary = "Listar usuarios",
            description = "Endpoint para listar todos os usuarios"
    )
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/listarPorUsuarioId/{usuarioId}")
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_RESPONSAVELEQUIPE', 'ROLE_LIDERCOMITE')") // qualquer usuário autenticado pode ver pelo id
    @Operation(
            summary = "Listar usuario pelo id de usuario",
            description = "Endpoint para listar usuario por Id de usuario"
    )
    public ResponseEntity<UsuarioDTOResponse> listarPorUsuarioId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioDTOResponse usuario = usuarioService.listarPorUsuarioId(usuarioId);
        if (usuario == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuario);
        }
    }

    @PostMapping("/criar")
    @Operation(
            summary = "Criar novo usuario",
            description = "Endpoint para criar um novo registro de usuario"
    )
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(
            @Valid @RequestBody UsuarioDTORequest usuario
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(usuario));
    }

    @PutMapping("/atualizar/{usuarioId}")
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_RESPONSAVELEQUIPE', 'ROLE_LIDERCOMITE')") // qualquer usuário autenticado pode atualizar seus dados
    @Operation(
            summary = "Atualizar todos os dados do usuario",
            description = "Endpoint para atualizar o registro do usuario"
    )
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(
            @PathVariable("usuarioId") Integer usuarioId,
            @Valid @RequestBody UsuarioDTORequest usuarioDTORequest
    ) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioId, usuarioDTORequest));
    }

    @PatchMapping("/atualizarStatus/{usuarioId}")
    @PreAuthorize("hasRole('ROLE_LIDERCOMITE')") // somente líderes podem atualizar status
    @Operation(
            summary = "Atualizar campo status do usuario",
            description = "Endpoint para atualizar apenas o status do usuario"
    )
    public ResponseEntity<UsuarioDTOUpdateResponse> atualizarStatusUsuario(
            @PathVariable("usuarioId") Integer usuarioId,
            @Valid @RequestBody UsuarioDTORequest usuarioDTOUpdateRequest
    ) {
        return ResponseEntity.ok(usuarioService.atualizarStatusUsuario(usuarioId, usuarioDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{usuarioId}")
    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_RESPONSAVELEQUIPE', 'ROLE_LIDERCOMITE')") // usuários podem apagar seu cadastro, líderes podem apagar qualquer
    @Operation(
            summary = "Apagar registro do usuario",
            description = "Endpoint para apagar registro do usuario"
    )
    public ResponseEntity<Void> apagarUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        usuarioService.apagarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<UsuarioDTOLoginResponse> login(@RequestBody UsuarioDTOLoginRequest usuarioDTOLoginRequest){
        return ResponseEntity.ok(usuarioService.login(usuarioDTOLoginRequest));
    }
}
