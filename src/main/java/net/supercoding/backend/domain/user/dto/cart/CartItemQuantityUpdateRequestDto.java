package net.supercoding.backend.domain.user.dto.cart;

import lombok.Getter;

@Getter
public class CartItemQuantityUpdateRequestDto {
    private Long cartPk;
    private int quantity;
}

