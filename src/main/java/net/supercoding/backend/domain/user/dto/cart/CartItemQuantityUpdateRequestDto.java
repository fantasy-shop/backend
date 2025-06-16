package net.supercoding.backend.domain.user.dto.cart;

import lombok.Getter;

@Getter
public class CartItemQuantityUpdateRequestDto {
    private Long cartItemId;
    private int quantity;
}
