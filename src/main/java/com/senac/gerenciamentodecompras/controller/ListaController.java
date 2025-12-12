package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.dto.request.ListaDTORequest;
import com.senac.gerenciamentodecompras.dto.request.ListaDTOUpdateRequest;
import com.senac.gerenciamentodecompras.dto.response.ListaDTOResponse;
import com.senac.gerenciamentodecompras.dto.response.ListaDTOUpdateResponse;
import com.senac.gerenciamentodecompras.entity.Lista;
import com.senac.gerenciamentodecompras.service.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lista")
public class ListaController {

    private final ListaService listaService;

    public ListaController(ListaService listaService) {
        this.listaService = listaService;
    }

    @GetMapping("/listar")
    @Operation(
            summary = "Listar listas",
            description = "Endpoint para listar todas as listas"
    )
    public ResponseEntity<List<ListaDTOResponse>> listarListas() {
        return ResponseEntity.ok(listaService.listarListas());
    }

    @GetMapping("/listarPorListaId/{listaId}")
    @Operation(
            summary = "Listar lista pelo id de lista",
            description = "Endpoint para listar lista por Id de lista"
    )
    public ResponseEntity<ListaDTOResponse> listarPorListaId(@PathVariable("listaId") Integer listaId) {
        ListaDTOResponse lista = listaService.listarPorListaId(listaId);
        if (lista == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/listarPorUsuario/{usuarioId}")
    public ResponseEntity<List<Lista>> listarMissoesPorUsuario(@PathVariable("usuarioId") Integer usuarioId){
        return ResponseEntity.ok(listaService.listarPorUsuarioId(usuarioId));
    }



    @PostMapping("/criar")
    @Operation(
            summary = "Criar nova lista",
            description = "Endpoint para criar um novo registro de lista"
    )
    public ResponseEntity<ListaDTOResponse> criarLista(
            @Valid @RequestBody ListaDTORequest lista
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(listaService.criarLista(lista));
    }

    @PutMapping("/atualizar/{listaId}")
    @Operation(
            summary = "Atualizar todos os dados da lista",
            description = "Endpoint para atualizar o registro da lista"
    )
    public ResponseEntity<ListaDTOResponse> atualizarLista(
            @PathVariable("listaId") Integer listaId,
            @Valid @RequestBody ListaDTORequest listaDTORequest
    ) {
        return ResponseEntity.ok(listaService.atualizarLista(listaId, listaDTORequest));
    }

    @PatchMapping("/atualizarStatus/{listaId}")
    @Operation(
            summary = "Atualizar campo status da lista",
            description = "Endpoint para atualizar apenas o status da lista"
    )
    public ResponseEntity<ListaDTOUpdateResponse> atualizarStatusLista(
            @PathVariable("listaId") Integer listaId,
            @Valid @RequestBody ListaDTOUpdateRequest listaDTOUpdateRequest
    ) {
        return ResponseEntity.ok(listaService.atualizarStatusLista(listaId, listaDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{listaId}")
    @Operation(
            summary = "Apagar registro da lista",
            description = "Endpoint para apagar registro da lista"
    )
    public ResponseEntity<Void> apagarLista(@PathVariable("listaId") Integer listaId) {
        listaService.apagarLista(listaId);
        return ResponseEntity.noContent().build();
    }
}
