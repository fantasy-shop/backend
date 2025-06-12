package net.supercoding.backend.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddToCartRequestDto {
    private Long itemPk;
    private int quantity;
}
