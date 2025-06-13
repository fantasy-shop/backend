package net.supercoding.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime paymentDate;
    private Long totalPrice;
    private String paymentStatus; // enum으로 대체 가능: PAID, CANCELED 등

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<PaymentItem> items = new ArrayList<>();
}
