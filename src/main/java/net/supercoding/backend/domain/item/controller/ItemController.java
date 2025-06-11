package net.supercoding.backend.domain.item.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateRequest;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateResponse;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemListResponse;
import net.supercoding.backend.domain.item.service.ItemService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("")
    public List<ItemListResponse> itemList(
            @RequestParam(value = "itemCategory", defaultValue = "all") String itemCategory,
            @RequestParam(value = "sortCategory", defaultValue = "noSorted") String sortCategory
    ){
        return itemService.itemList(itemCategory, sortCategory);
    }

    @DeleteMapping("/{itemPk}")
    public String itemDelete(@PathVariable("itemPk") Long itemPk) {
        return itemService.itemDelete(itemPk);
    }


}
