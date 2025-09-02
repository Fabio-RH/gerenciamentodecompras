package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.entity.Lista;
import com.senac.gerenciamentodecompras.repository.ListaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListaService {

    private ListaRepository listaRepository;

    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public List<Lista> listarListas(){
        return this.listaRepository.findAll();


    }
}