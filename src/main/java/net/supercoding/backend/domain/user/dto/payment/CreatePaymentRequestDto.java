package net.supercoding.backend.domain.user.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePaymentRequestDto {

    private List<PaymentItemRequestDto> items;

    @Getter
    @Setter
    public static class PaymentItemRequestDto {
        private Long itemPk;
        private Long quantity;
    }
}
