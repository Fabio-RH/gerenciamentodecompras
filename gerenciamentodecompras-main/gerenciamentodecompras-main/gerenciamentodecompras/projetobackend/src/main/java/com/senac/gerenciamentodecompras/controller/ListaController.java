package com.senac.gerenciamentodecompras.controller;

import com.senac.gerenciamentodecompras.entity.Lista;
import com.senac.gerenciamentodecompras.service.ListaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/lista")
public class ListaController {

    public ListaController(ListaService listaService) {this.listaService = listaService; }

    private ListaService listaService;
    @GetMapping("/listar")
    public ResponseEntity<List<Lista>> listarListas(){
        return ResponseEntity.ok(listaService.listarListas());

    }

}
