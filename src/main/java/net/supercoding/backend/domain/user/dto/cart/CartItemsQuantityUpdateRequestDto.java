package net.supercoding.backend.domain.user.dto.cart;

import lombok.Getter;

import java.util.List;

@Getter
public class CartItemsQuantityUpdateRequestDto {
    private List<CartItemQuantityUpdateRequestDto> items;
}
