package net.supercoding.backend.domain.user.dto.cart;

import lombok.Builder;
import lombok.Getter;
import net.supercoding.backend.domain.user.entity.CartItem;

import java.time.LocalDateTime;

@Builder
@Getter
public class CartItemResponseDto {
    private Long cartPk;
    private Long itemPk;
    private String itemName;
    private int quantity;
    private Long itemPrice;
    private LocalDateTime addedAt;

    public static CartItemResponseDto from(CartItem cartItem) {
        return CartItemResponseDto.builder()
                .cartPk(cartItem.getId())
                .itemPk(cartItem.getItem().getItemPk())
                .itemName(cartItem.getItem().getItemName())
                .quantity(cartItem.getQuantity())
                .itemPrice(cartItem.getItem().getItemPrice())
                .addedAt(cartItem.getAddedAt())
                .build();
    }
}
