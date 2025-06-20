package net.supercoding.backend.domain.item.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateUpdateRequest;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemDetailResponse;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemListResponse;
import net.supercoding.backend.domain.item.service.ItemService;
import net.supercoding.backend.domain.user.repository.CartItemRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CartItemRepository cartItemRepository;

    @PostMapping("")
    public ItemDetailResponse itemCreate(
            @ModelAttribute ItemCreateUpdateRequest itemCreateUpdateRequest,
            @RequestPart(value = "itemImage", required = false) MultipartFile itemImage
    ) throws IOException {
        return itemService.itemCreate(itemCreateUpdateRequest, itemImage);
    }

    @GetMapping("")
    public List<ItemListResponse> itemList(
            @RequestParam(value = "sortCategory", defaultValue = "noSorted") String sortCategory,
            @RequestParam(value = "itemCategory", defaultValue = "all") String itemCategory,
            @RequestParam(value = "itemNameKeyword", defaultValue = "") String itemNameKeyword
    ){
        return itemService.itemList(itemCategory, sortCategory, itemNameKeyword);
    }

    @DeleteMapping("/{itemPk}")
    public String itemDelete(
            @PathVariable("itemPk") Long itemPk
    ) {
        cartItemRepository.deleteByItem_ItemPk(itemPk);
        return itemService.itemDelete(itemPk);
    }

    @GetMapping("/{itemPk}")
    public ItemDetailResponse itemDetail(
            @PathVariable("itemPk") Long itemPk
    ) {
        return itemService.itemDetail(itemPk);
    }

    @PutMapping("/{itemPk}")
    public ItemDetailResponse itemUpdate(
            @PathVariable("itemPk") Long itemPk,
            @ModelAttribute ItemCreateUpdateRequest itemCreateUpdateRequest,
            @RequestPart(value = "itemImage", required = false) MultipartFile itemImage
    ) throws IOException {
        return itemService.itemUpdate(itemPk, itemCreateUpdateRequest, itemImage);
    }
}
