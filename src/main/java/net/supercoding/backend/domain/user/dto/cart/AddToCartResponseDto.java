package net.supercoding.backend.domain.user.dto.cart;

import lombok.Builder;
import lombok.Getter;
import net.supercoding.backend.domain.user.entity.CartItem;

import java.time.LocalDateTime;

@Getter
@Builder
public class AddToCartResponseDto {
    private Long cartPk;
    private Long userPk;
    private Long itemPk;
    private int quantity;
    private LocalDateTime addedAt;

    public static AddToCartResponseDto from(CartItem cartItem) {
        return AddToCartResponseDto.builder()
                .cartPk(cartItem.getId())
                .userPk(cartItem.getUser().getUserPk())
                .itemPk(cartItem.getItem().getItemPk())
                .quantity(cartItem.getQuantity())
                .addedAt(cartItem.getAddedAt())
                .build();
    }
}
