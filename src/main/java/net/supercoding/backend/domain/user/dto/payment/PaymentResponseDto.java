package net.supercoding.backend.domain.user.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PaymentResponseDto {
    private Long paymentPk;
    private LocalDateTime paymentDate;
    private Long totalPrice;
    private String paymentStatus;
    private List<PaymentItemDto> items;

    @Getter
    @Setter
    public static class PaymentItemDto {
        private Long itemPk;
        private String itemName;
        private Long quantity;
        private Long priceAtOrderTime;
    }
}
