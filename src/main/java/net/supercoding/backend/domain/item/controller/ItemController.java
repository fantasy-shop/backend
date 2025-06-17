package net.supercoding.backend.domain.item.controller;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateUpdateRequest;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateUpdateResponse;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemDetailResponse;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemListResponse;
import net.supercoding.backend.domain.item.service.ItemService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("")
    public ItemCreateUpdateResponse itemCreate(
            @ModelAttribute ItemCreateUpdateRequest itemCreateUpdateRequest,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return itemService.itemCreate(itemCreateUpdateRequest, image);
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
        return itemService.itemDelete(itemPk);
    }

    @GetMapping("/{itemPk}")
    public ItemDetailResponse itemDetail(
            @PathVariable("itemPk") Long itemPk
    ) {
        return itemService.itemDetail(itemPk);
    }

    @PutMapping("/{itemPk}")
    public ItemCreateUpdateResponse itemUpdate(
            @PathVariable("itemPk") Long itemPk,
            @ModelAttribute ItemCreateUpdateRequest itemCreateUpdateRequest,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return itemService.itemUpdate(itemPk, itemCreateUpdateRequest, image);
    }
}
