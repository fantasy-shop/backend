package net.supercoding.backend.domain.user.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentSimpleDto {
    private Long paymentPk;
    private LocalDateTime paymentDate;
    private Long totalPrice;
    private String paymentStatus;
}
