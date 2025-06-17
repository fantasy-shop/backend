package net.supercoding.backend.domain.user.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartRequestDto {
    private Long itemPk;
    private int quantity;
}
