package com.senac.gerenciamentodecompras.service;

import com.senac.gerenciamentodecompras.dto.request.ItemDTORequest;
import com.senac.gerenciamentodecompras.dto.response.*;
import com.senac.gerenciamentodecompras.entity.Item;
import com.senac.gerenciamentodecompras.entity.Lista;
import com.senac.gerenciamentodecompras.repository.ItemRepository;
import com.senac.gerenciamentodecompras.repository.ListaRepository;
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
        Item i = new Item();
        i.setItem_quantidade(itemDTORequest.getItem_quantidade());
        i.setItem_status(1);
        if (i.getLista()!= null ){

        }



        Item item = modelMapper.map(itemDTORequest, Item.class);
        Item itemSave = this.itemRepository.save(item);
        return modelMapper.map(itemSave, ItemDTOResponse.class);
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

    public void apagarItem(Integer itemId) {
        itemRepository.apagadoLogicoItem(itemId);
    }
}
