package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.dto.request.ListaDTORequest;
import com.senac.gerenciamentodecompras.dto.request.ListaDTOUpdateRequest;
import com.senac.gerenciamentodecompras.dto.response.*;
import com.senac.gerenciamentodecompras.entity.Lista;
import com.senac.gerenciamentodecompras.entity.Usuario;
import com.senac.gerenciamentodecompras.repository.ListaRepository;
import com.senac.gerenciamentodecompras.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ListaService {

    private final ListaRepository listaRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public ListaService(ListaRepository listaRepository,
                        ModelMapper modelMapper) {
        this.listaRepository = listaRepository;
        this.modelMapper = modelMapper;
    }

    public List<ListaDTOResponse> listarListas() {
        return listaRepository.listarListas()
                .stream()
                .map(lista -> modelMapper.map(lista, ListaDTOResponse.class))
                .toList();
    }

    public ListaDTOResponse listarPorListaId(Integer listaId) {
        Lista lista = listaRepository.obterListaPeloId(listaId);
        return (lista != null) ? modelMapper.map(lista, ListaDTOResponse.class) : null;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ListaDTOResponse criarLista(ListaDTORequest listaDTORequest) {

        Lista lista = new Lista();

        lista.setLista_nome(listaDTORequest.getLista_nome());
        lista.setLista_dataCriacao(listaDTORequest.getLista_dataCriacao());
        lista.setLista_status(listaDTORequest.getLista_status());

        // Se veio um usuário_id, buscar o usuário
        if (listaDTORequest.getUsuario_id() != null) {
            Usuario usuario = usuarioRepository.findById(listaDTORequest.getUsuario_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
            lista.setUsuario(usuario);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuario_id é obrigatório");
        }

        Lista listaSave = this.listaRepository.save(lista);

        return modelMapper.map(listaSave, ListaDTOResponse.class);
    }




    @Transactional
    public ListaDTOResponse atualizarLista(Integer listaId, ListaDTORequest listaDTORequest) {
        Lista lista = listaRepository.obterListaPeloId(listaId);
        if (lista != null) {
            // Atualize só os campos que você quer modificar, um a um:
            lista.setLista_nome(listaDTORequest.getLista_nome());
            lista.setLista_dataCriacao(listaDTORequest.getLista_dataCriacao());
            lista.setLista_status(listaDTORequest.getLista_status());

            // Atualiza o usuário, se informado:
            if (listaDTORequest.getUsuario_id() != null) {
                Usuario usuario = usuarioRepository.findById(listaDTORequest.getUsuario_id())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
                lista.setUsuario(usuario);
            }

            // Salva a entidade atualizada
            Lista listaAtualizada = listaRepository.save(lista);

            return modelMapper.map(listaAtualizada, ListaDTOResponse.class);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista não encontrada");
        }
    }



    @Transactional
    public ListaDTOUpdateResponse atualizarStatusLista(Integer listaId, ListaDTOUpdateRequest listaDTOUpdateRequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Lista lista = listaRepository.obterListaPeloId(listaId);
        // se encontra o registro a ser atualizado
        if (lista != null) {
            // atualiza o status da lista a partir do DTO
            lista.setLista_status(listaDTOUpdateRequest.getLista_status());
            Lista listaSave = listaRepository.save(lista);
            return modelMapper.map(listaSave, ListaDTOUpdateResponse.class);
        } else {
            // Error 400 caso tente atualizar lista inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void apagarLista(Integer listaId) {
        listaRepository.apagadoLogicoLista(listaId);
    }

    public List<Lista> listarPorUsuarioId(Integer usuarioId) {
        return this.listaRepository.obterListaPorUsuarioId(usuarioId);
    }
}
