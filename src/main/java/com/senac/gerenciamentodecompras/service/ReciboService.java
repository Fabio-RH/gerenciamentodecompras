package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.dto.request.ListaDTORequest;
import com.senac.gerenciamentodecompras.dto.request.ReciboDTORequest;
import com.senac.gerenciamentodecompras.dto.request.ReciboDTOUpdateRequest;
import com.senac.gerenciamentodecompras.dto.response.*;
import com.senac.gerenciamentodecompras.entity.Lista;
import com.senac.gerenciamentodecompras.entity.Recibo;
import com.senac.gerenciamentodecompras.entity.Usuario;
import com.senac.gerenciamentodecompras.repository.ListaRepository;
import com.senac.gerenciamentodecompras.repository.ReciboRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReciboService {

    private final ReciboRepository reciboRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private ListaRepository listaRepository;

    public ReciboService(ReciboRepository reciboRepository,
                         ModelMapper modelMapper) {
        this.reciboRepository = reciboRepository;
        this.modelMapper = modelMapper;
    }

    public List<ReciboDTOResponse> listarRecibos() {
        return reciboRepository.listarRecibos()
                .stream()
                .map(recibo -> modelMapper.map(recibo, ReciboDTOResponse.class))
                .toList();
    }

    public ReciboDTOResponse listarPorReciboId(Integer reciboId) {
        Recibo recibo = reciboRepository.obterReciboPeloId(reciboId);
        return (recibo != null) ? modelMapper.map(recibo, ReciboDTOResponse.class) : null;
    }

    public ReciboDTOResponse criarRecibo(ReciboDTORequest reciboDTORequest) {
        Recibo recibo = new Recibo();
        recibo.setRecibo_caminho_imagem(reciboDTORequest.getRecibo_caminho_imagem());
        recibo.setRecibo_valor(reciboDTORequest.getRecibo_valor());
        recibo.setRecibo_observacao(reciboDTORequest.getRecibo_observacao());
        recibo.setRecibo_data_upload(reciboDTORequest.getRecibo_data_upload());
        recibo.setRecibo_status(reciboDTORequest.getRecibo_status());
        Lista lista = listaRepository.obterListaPeloId(reciboDTORequest.getLista_id());
        if (lista == null){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista não encontrada");
        }
        recibo.setLista(lista);
        Recibo reciboSave = reciboRepository.save(recibo);

        ReciboDTOResponse reciboDTOResponse = modelMapper.map(reciboSave, ReciboDTOResponse.class);
        return reciboDTOResponse;
    }

    @Transactional
    public ReciboDTOResponse atualizarRecibo(Integer reciboId, ReciboDTORequest reciboDTORequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Recibo recibo = reciboRepository.obterReciboPeloId(reciboId);
        // se encontra o registro a ser atualizado
        if (recibo != null) {
            // atualiza dados do recibo a partir do DTO
            modelMapper.map(reciboDTORequest, recibo);
            // atualiza o recibo vinculado
            Recibo tempResponse = reciboRepository.save(recibo);
            return modelMapper.map(tempResponse, ReciboDTOResponse.class);
        } else {
            // Error 400 caso tente atualizar recibo inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ReciboDTOUpdateResponse atualizarStatusRecibo(Integer reciboId, ReciboDTOUpdateRequest reciboDTOUpdateRequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Recibo recibo = reciboRepository.obterReciboPeloId(reciboId);
        // se encontra o registro a ser atualizado
        if (recibo != null) {
            // atualiza o status do recibo a partir do DTO
            recibo.setRecibo_status(reciboDTOUpdateRequest.getRecibo_status());
            Recibo reciboSave = reciboRepository.save(recibo);
            return modelMapper.map(reciboSave, ReciboDTOUpdateResponse.class);
        } else {
            // Error 400 caso tente atualizar recibo inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void apagarRecibo(Integer reciboId) {
        reciboRepository.apagadoLogicoRecibo(reciboId);
    }
}
