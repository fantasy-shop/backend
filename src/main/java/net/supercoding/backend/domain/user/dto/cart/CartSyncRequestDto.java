package net.supercoding.backend.domain.user.dto.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartSyncRequestDto {
    private List<AddToCartRequestDto> items; // itemPk, quantity 포함
}
