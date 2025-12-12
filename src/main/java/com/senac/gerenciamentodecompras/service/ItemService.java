package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.dto.request.ItemDTORequest;
import com.senac.gerenciamentodecompras.dto.response.*;
import com.senac.gerenciamentodecompras.entity.Item;
import com.senac.gerenciamentodecompras.entity.Lista;
import com.senac.gerenciamentodecompras.entity.Produto;
import com.senac.gerenciamentodecompras.repository.ItemRepository;
import com.senac.gerenciamentodecompras.repository.ListaRepository;
import com.senac.gerenciamentodecompras.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    @Autowired
    private ListaRepository listaRepository; // Necessário injetar

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public ItemService(ItemRepository itemRepository,
                       ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    public List<ItemDTOResponse> listarItens() {
        return itemRepository.listarItens()
                .stream()
                .map(item -> modelMapper.map(item, ItemDTOResponse.class))
                .toList();
    }

    public ItemDTOResponse listarPorItemId(Integer itemId) {
        Item item = itemRepository.obterItemPeloId(itemId);
        return (item != null) ? modelMapper.map(item, ItemDTOResponse.class) : null;
    }

    @Transactional
    public ItemDTOResponse criarItem(ItemDTORequest itemDTORequest) {
        // 1. Buscar as entidades relacionadas pelos IDs vindos do DTO
        // É importante validar se elas existem antes de tentar salvar o item
        Lista lista = listaRepository.findById(itemDTORequest.getLista_id())
                .orElseThrow(() -> new RuntimeException("Lista não encontrada com ID: " + itemDTORequest.getLista_id()));

        Produto produto = produtoRepository.findById(itemDTORequest.getProduto_id())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemDTORequest.getProduto_id()));

        // 2. Montagem MANUAL da Entidade (Evita o erro do ModelMapper e resolve o relacionamento)
        Item novoItem = new Item();
        novoItem.setItem_quantidade(itemDTORequest.getItem_quantidade());
        novoItem.setItem_status(1); // Status padrão

        // Aqui fazemos a associação correta dos objetos
        novoItem.setLista(lista);
        novoItem.setProduto(produto);

        // 3. Salvar no banco
        Item itemSalvo = itemRepository.save(novoItem);

        // 4. Converter para DTO de Resposta (Aqui o ModelMapper funciona bem e é seguro)
        return modelMapper.map(itemSalvo, ItemDTOResponse.class);
    }

    @Transactional
    public ItemDTOResponse atualizarItem(Integer itemId, ItemDTORequest itemDTORequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Item item = itemRepository.obterItemPeloId(itemId);
        // se encontra o registro a ser atualizado
        if (item != null) {
            // atualiza dados do item a partir do DTO
            modelMapper.map(itemDTORequest, item);
            // atualiza o item vinculado
            Item tempResponse = itemRepository.save(item);
            return modelMapper.map(tempResponse, ItemDTOResponse.class);
        } else {
            // Error 400 caso tente atualizar item inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ItemDTOUpdateResponse atualizarStatusItem(Integer itemId, ItemDTORequest itemDTOUpdateRequest) {
        // antes de atualizar busca se existe o registro a ser atualizado
        Item item = itemRepository.obterItemPeloId(itemId);
        // se encontra o registro a ser atualizado
        if (item != null) {
            // atualiza o status do item a partir do DTO
            item.setItem_status(itemDTOUpdateRequest.getItem_status());
            Item itemSave = itemRepository.save(item);
            return modelMapper.map(itemSave, ItemDTOUpdateResponse.class);
        } else {
            // Error 400 caso tente atualizar item inexistente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<ItemDTOResponseP> listarItensPorListaId(Integer lista_id) {
        List<Item> itens = this.itemRepository.listarItensPorLista(lista_id);

        return itens.stream()
                .map(item -> {
                    // 1. Usa o ModelMapper para copiar o básico (id, quantidade, status)
                    ItemDTOResponseP dto = modelMapper.map(item, ItemDTOResponseP.class);

                    // 2. Preenche manualmente o nome pegando do Produto relacionado
                    if (item.getProduto() != null) {
                        dto.setItem_nome(item.getProduto().getProduto_nome());
                    }

                    return dto;
                })
                .toList();
    }
    public void apagarItem(Integer itemId) {
        itemRepository.apagadoLogicoItem(itemId);
    }
}
