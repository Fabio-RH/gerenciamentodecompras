package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.entity.Recibo;
import com.senac.gerenciamentodecompras.service.ReciboService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/recibo")
public class ReciboController {

    public ReciboController(ReciboService reciboService) {
        this.reciboService = reciboService;
    }

    private ReciboService reciboService;
    @GetMapping("/listar")
    public ResponseEntity<List<Recibo>> listarRecibos(){
        return ResponseEntity.ok(reciboService.listarRecibos());

    }

}
