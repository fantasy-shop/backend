package net.supercoding.backend.domain.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CartItemsQuantityUpdateRequestDto {
    private List<CartItemQuantityUpdateRequestDto> items;
}
