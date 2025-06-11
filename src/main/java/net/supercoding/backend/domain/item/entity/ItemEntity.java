package net.supercoding.backend.domain.item.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemPk;
    private String itemName;
    private String itemEffect;
    private Long itemPrice;
    private String itemDescription;
    private Long itemInventory;
    private String itemImageUrl;
    private String itemCategory;
    private LocalDateTime itemCreatedAt;

    // Entity 생성시 자동으로 itemCreatedAt 값을 현재 시간으로 넣어주는 메서드
    @PrePersist
    protected void onCreate() {
        this.itemCreatedAt = LocalDateTime.now();
    }

}

/**
 *   @NoArgsConstructor
 *   @AllArgsConstructor
 *   @Getter
 *   @Setter
 *   @Builder
 *   를 안쓰면 아래와 같이 길게 코드를 작성해야함
 *   아래 예시코드는 ChatGPT에서 가져옴
 */
//@Entity
//public class ItemEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long itemPk;
//
//    private String itemName;
//    private String itemEffect;
//    private Long itemPrice;
//    private String itemDescription;
//    private Long itemInventory;
//    private String itemImageUrl;
//    private String itemCategory;
//    private LocalDateTime itemCreatedAt;
//
//    // ✅ 기본 생성자 (NoArgsConstructor)
//    public ItemEntity() {
//    }
//
//    // ✅ 전체 필드 생성자 (AllArgsConstructor)
//    public ItemEntity(Long itemPk, String itemName, String itemEffect, Long itemPrice,
//            String itemDescription, Long itemInventory, String itemImageUrl,
//            String itemCategory, LocalDateTime itemCreatedAt) {
//        this.itemPk = itemPk;
//        this.itemName = itemName;
//        this.itemEffect = itemEffect;
//        this.itemPrice = itemPrice;
//        this.itemDescription = itemDescription;
//        this.itemInventory = itemInventory;
//        this.itemImageUrl = itemImageUrl;
//        this.itemCategory = itemCategory;
//        this.itemCreatedAt = itemCreatedAt;
//    }
//
//    // ✅ Builder 패턴 구현
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//        private Long itemPk;
//        private String itemName;
//        private String itemEffect;
//        private Long itemPrice;
//        private String itemDescription;
//        private Long itemInventory;
//        private String itemImageUrl;
//        private String itemCategory;
//        private LocalDateTime itemCreatedAt;
//
//        public Builder itemPk(Long itemPk) {
//            this.itemPk = itemPk;
//            return this;
//        }
//
//        public Builder itemName(String itemName) {
//            this.itemName = itemName;
//            return this;
//        }
//
//        public Builder itemEffect(String itemEffect) {
//            this.itemEffect = itemEffect;
//            return this;
//        }
//
//        public Builder itemPrice(Long itemPrice) {
//            this.itemPrice = itemPrice;
//            return this;
//        }
//
//        public Builder itemDescription(String itemDescription) {
//            this.itemDescription = itemDescription;
//            return this;
//        }
//
//        public Builder itemInventory(Long itemInventory) {
//            this.itemInventory = itemInventory;
//            return this;
//        }
//
//        public Builder itemImageUrl(String itemImageUrl) {
//            this.itemImageUrl = itemImageUrl;
//            return this;
//        }
//
//        public Builder itemCategory(String itemCategory) {
//            this.itemCategory = itemCategory;
//            return this;
//        }
//
//        public Builder itemCreatedAt(LocalDateTime itemCreatedAt) {
//            this.itemCreatedAt = itemCreatedAt;
//            return this;
//        }
//
//        public ItemEntity build() {
//            return new ItemEntity(itemPk, itemName, itemEffect, itemPrice, itemDescription,
//                    itemInventory, itemImageUrl, itemCategory, itemCreatedAt);
//        }
//    }
//
//    // ✅ Getter & Setter
//    public Long getItemPk() {
//        return itemPk;
//    }
//
//    public void setItemPk(Long itemPk) {
//        this.itemPk = itemPk;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }
//
//    public String getItemEffect() {
//        return itemEffect;
//    }
//
//    public void setItemEffect(String itemEffect) {
//        this.itemEffect = itemEffect;
//    }
//
//    public Long getItemPrice() {
//        return itemPrice;
//    }
//
//    public void setItemPrice(Long itemPrice) {
//        this.itemPrice = itemPrice;
//    }
//
//    public String getItemDescription() {
//        return itemDescription;
//    }
//
//    public void setItemDescription(String itemDescription) {
//        this.itemDescription = itemDescription;
//    }
//
//    public Long getItemInventory() {
//        return itemInventory;
//    }
//
//    public void setItemInventory(Long itemInventory) {
//        this.itemInventory = itemInventory;
//    }
//
//    public String getItemImageUrl() {
//        return itemImageUrl;
//    }
//
//    public void setItemImageUrl(String itemImageUrl) {
//        this.itemImageUrl = itemImageUrl;
//    }
//
//    public String getItemCategory() {
//        return itemCategory;
//    }
//
//    public void setItemCategory(String itemCategory) {
//        this.itemCategory = itemCategory;
//    }
//
//    public LocalDateTime getItemCreatedAt() {
//        return itemCreatedAt;
//    }
//
//    public void setItemCreatedAt(LocalDateTime itemCreatedAt) {
//        this.itemCreatedAt = itemCreatedAt;
//    }
//
//    // ✅ JPA에서 저장 직전 자동 시간 설정
//    @PrePersist
//    protected void onCreate() {
//        this.itemCreatedAt = LocalDateTime.now();
//    }
//}