package net.supercoding.backend.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentItem {
    @Id
    @GeneratedValue
    private Long id;

    private Long itemPk; // 실제 아이템 ID
    private String itemName;
    private Long quantity;
    private Long priceAtOrderTime;

    @ManyToOne
    private Payment payment;
}
