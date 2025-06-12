package net.supercoding.backend.domain.user.dto;

import lombok.Getter;

@Getter
public class CartItemQuantityUpdateRequestDto {
    private Long cartItemId;
    private int quantity;
}
