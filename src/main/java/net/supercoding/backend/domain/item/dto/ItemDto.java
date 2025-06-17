package net.supercoding.backend.domain.item.dto;

import java.time.LocalDateTime;
import lombok.*;
import net.supercoding.backend.domain.item.entity.ItemEntity;

public class ItemDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCreateUpdateRequest {

        private String itemName;
        private String itemEffect;
        private Long itemPrice;
        private String itemDescription;
        private Long itemInventory;
        private String itemCategory;

        public static ItemEntity toEntity(
                ItemCreateUpdateRequest itemCreateUpdateRequest
        ){
            return ItemEntity.builder()
                    .itemName(itemCreateUpdateRequest.getItemName())
                    .itemEffect(itemCreateUpdateRequest.getItemEffect())
                    .itemPrice(itemCreateUpdateRequest.getItemPrice())
                    .itemDescription(itemCreateUpdateRequest.getItemDescription())
                    .itemInventory(itemCreateUpdateRequest.getItemInventory())
                    .itemCategory(itemCreateUpdateRequest.getItemCategory())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCreateUpdateResponse {

        private Long itemPk;

        public static ItemCreateUpdateResponse fromEntity(
                ItemEntity itemEntity
        ) {
            return ItemCreateUpdateResponse.builder()
                    .itemPk(itemEntity.getItemPk())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemListResponse{

        private Long itemPk;
        private String itemName;
        private Long itemPrice;
        private String itemCategory;
        private Long itemInventory;
        private String itemImageUrl;
        private LocalDateTime createdAt;

        public static ItemListResponse fromEntity(ItemEntity itemEntity) {

            return ItemListResponse.builder()
                    .itemPk(itemEntity.getItemPk())
                    .itemName(itemEntity.getItemName())
                    .itemPrice(itemEntity.getItemPrice())
                    .itemCategory(itemEntity.getItemCategory())
                    .itemInventory(itemEntity.getItemInventory())
                    .itemImageUrl(itemEntity.getItemImageUrl())
                    .createdAt(itemEntity.getItemCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetailResponse {

        private Long itemPk;
        private String itemName;
        private String itemEffect;
        private Long itemPrice;
        private String itemDescription;
        private Long itemInventory;
        private String itemCategory;
        private String itemImageUrl;
        private LocalDateTime createdAt;

        public static ItemDetailResponse fromEntity(ItemEntity itemEntity) {

            return ItemDetailResponse.builder()
                    .itemPk(itemEntity.getItemPk())
                    .itemName(itemEntity.getItemName())
                    .itemEffect(itemEntity.getItemEffect())
                    .itemPrice(itemEntity.getItemPrice())
                    .itemDescription(itemEntity.getItemDescription())
                    .itemInventory(itemEntity.getItemInventory())
                    .itemCategory(itemEntity.getItemCategory())
                    .itemImageUrl(itemEntity.getItemImageUrl())
                    .createdAt(itemEntity.getItemCreatedAt())
                    .build();
        }
    }
}
