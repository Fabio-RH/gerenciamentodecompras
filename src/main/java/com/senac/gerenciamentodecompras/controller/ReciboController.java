package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.dto.request.ReciboDTORequest;
import com.senac.gerenciamentodecompras.dto.response.ReciboDTOResponse;
import com.senac.gerenciamentodecompras.dto.response.ReciboDTOUpdateResponse;
import com.senac.gerenciamentodecompras.entity.Recibo;
import com.senac.gerenciamentodecompras.service.ReciboService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recibo")
public class ReciboController {

    private final ReciboService reciboService;

    public ReciboController(ReciboService reciboService) {
        this.reciboService = reciboService;
    }

    @GetMapping("/listar")
    @Operation(
            summary = "Listar recibos",
            description = "Endpoint para listar todos os recibos"
    )
    public ResponseEntity<List<ReciboDTOResponse>> listarRecibos() {
        return ResponseEntity.ok(reciboService.listarRecibos());
    }

    @GetMapping("/listarPorReciboId/{reciboId}")
    @Operation(
            summary = "Listar recibo pelo id de recibo",
            description = "Endpoint para listar recibo por Id de recibo"
    )
    public ResponseEntity<ReciboDTOResponse> listarPorReciboId(@PathVariable("reciboId") Integer reciboId) {
        ReciboDTOResponse recibo = reciboService.listarPorReciboId(reciboId);
        if (recibo == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(recibo);
        }
    }

    @PostMapping("/criar")
    @Operation(
            summary = "Criar novo recibo",
            description = "Endpoint para criar um novo registro de recibo"
    )
    public ResponseEntity<ReciboDTOResponse> criarRecibo(
            @Valid @RequestBody ReciboDTORequest recibo
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reciboService.criarRecibo(recibo));
    }

    @PutMapping("/atualizar/{reciboId}")
    @Operation(
            summary = "Atualizar todos os dados do recibo",
            description = "Endpoint para atualizar o registro do recibo"
    )
    public ResponseEntity<ReciboDTOResponse> atualizarRecibo(
            @PathVariable("reciboId") Integer reciboId,
            @Valid @RequestBody ReciboDTORequest reciboDTORequest
    ) {
        return ResponseEntity.ok(reciboService.atualizarRecibo(reciboId, reciboDTORequest));
    }

    @PatchMapping("/atualizarStatus/{reciboId}")
    @Operation(
            summary = "Atualizar campo status do recibo",
            description = "Endpoint para atualizar apenas o status do recibo"
    )
    public ResponseEntity<ReciboDTOUpdateResponse> atualizarStatusRecibo(
            @PathVariable("reciboId") Integer reciboId,
            @Valid @RequestBody ReciboDTORequest reciboDTOUpdateRequest
    ) {
        return ResponseEntity.ok(reciboService.atualizarStatusRecibo(reciboId, reciboDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{reciboId}")
    @Operation(
            summary = "Apagar registro do recibo",
            description = "Endpoint para apagar registro do recibo"
    )
    public ResponseEntity<Void> apagarRecibo(@PathVariable("reciboId") Integer reciboId) {
        reciboService.apagarRecibo(reciboId);
        return ResponseEntity.noContent().build();
    }
}
