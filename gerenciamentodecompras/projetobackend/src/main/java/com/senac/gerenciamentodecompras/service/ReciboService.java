package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.entity.Recibo;
import com.senac.gerenciamentodecompras.repository.ReciboRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReciboService {

    private ReciboRepository reciboRepository;

    public ReciboService(ReciboRepository reciboRepository) {
        this.reciboRepository = reciboRepository;
    }

    public List<Recibo> listarRecibos(){
        return this.reciboRepository.findAll();


    }
}