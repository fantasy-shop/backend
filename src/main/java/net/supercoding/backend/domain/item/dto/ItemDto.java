package net.supercoding.backend.domain.item.dto;

import lombok.*;
import net.supercoding.backend.domain.item.entity.ItemEntity;

public class ItemDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCreateRequest {

        private String itemName;
        private String itemEffect;
        private Long itemPrice;
        private String itemDescription;
        private Long itemInventory;
        private String itemCategory;

        public static ItemEntity toEntity(
                ItemCreateRequest itemCreateRequest
        ){
            return ItemEntity.builder()
                    .itemName(itemCreateRequest.getItemName())
                    .itemEffect(itemCreateRequest.getItemEffect())
                    .itemPrice(itemCreateRequest.getItemPrice())
                    .itemDescription(itemCreateRequest.getItemDescription())
                    .itemInventory(itemCreateRequest.getItemInventory())
                    .itemCategory(itemCreateRequest.getItemCategory())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCreateResponse {

        private Long itemPk;

        public static ItemCreateResponse fromEntity(
                ItemEntity itemEntity
        ) {
            return ItemCreateResponse.builder()
                    .itemPk(itemEntity.getItemPk())
                    .build();
        }
    }
}
