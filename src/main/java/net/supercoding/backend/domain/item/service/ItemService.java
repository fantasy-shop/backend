package net.supercoding.backend.domain.item.service;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateRequest;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateResponse;
import net.supercoding.backend.domain.item.entity.ItemEntity;
import net.supercoding.backend.domain.item.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemCreateResponse itemCreate(ItemCreateRequest itemCreateRequest) {

        ItemEntity newItemEntity = ItemCreateRequest.toEntity(itemCreateRequest);

        ItemEntity savedItemEntity = itemRepository.save(newItemEntity);

        return ItemCreateResponse.fromEntity(savedItemEntity);

//        위 코드를 한줄로하면
//        return ItemCreateResponse.fromEntity(
//                itemRepository.save(
//                        ItemCreateRequest.toEntity(itemCreateRequest)
//                )
//        );
    }
}
