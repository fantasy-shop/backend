package net.supercoding.backend.domain.item.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateRequest;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateResponse;
import net.supercoding.backend.domain.item.service.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("")
    public ItemCreateResponse itemCreate(
            @RequestBody ItemCreateRequest itemCreateRequest
    ) {
        return itemService.itemCreate(itemCreateRequest);
    }

}
