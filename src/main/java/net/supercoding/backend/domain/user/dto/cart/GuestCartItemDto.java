package net.supercoding.backend.domain.user.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestCartItemDto {
    private Long itemPk;
    private int quantity;
}
