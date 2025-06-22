package net.supercoding.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.supercoding.backend.domain.item.entity.ItemEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemEntity item;

    private int quantity;

    private LocalDateTime addedAt;

    @Builder
    public CartItem(User user, ItemEntity item, int quantity) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
    }
}
