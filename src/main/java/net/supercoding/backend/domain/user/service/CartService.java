package net.supercoding.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.entity.ItemEntity;
import net.supercoding.backend.domain.item.repository.ItemRepository;
import net.supercoding.backend.domain.user.dto.cart.AddToCartResponseDto;
import net.supercoding.backend.domain.user.dto.cart.CartItemQuantityUpdateRequestDto;
import net.supercoding.backend.domain.user.dto.cart.CartItemResponseDto;
import net.supercoding.backend.domain.user.dto.cart.GuestCartItemDto;
import net.supercoding.backend.domain.user.entity.CartItem;
import net.supercoding.backend.domain.user.entity.User;
import net.supercoding.backend.domain.user.repository.CartItemRepository;
import net.supercoding.backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddToCartResponseDto addToCart(User user, Long itemId, int quantity) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        CartItem cartItem = cartItemRepository.findByUserAndItem(user, item)
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    return existingItem;
                })
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem(user, item, quantity);
                    newCartItem.setAddedAt(LocalDateTime.now());
                    return cartItemRepository.save(newCartItem);
                });

        return AddToCartResponseDto.from(cartItem);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponseDto> getCartItems(User user) {
        List<CartItem> cartItems = cartItemRepository.findAllByUser(user);
        return cartItems.stream()
                .map(CartItemResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateQuantities(User user, List<CartItemQuantityUpdateRequestDto> updates) {
        for (CartItemQuantityUpdateRequestDto update : updates) {
            CartItem cartItem = cartItemRepository.findById(update.getCartItemId())
                    .orElseThrow(() -> new RuntimeException("장바구니 아이템을 찾을 수 없습니다: " + update.getCartItemId()));

            if (!cartItem.getUser().getUserPk().equals(user.getUserPk())) {
                throw new RuntimeException("권한이 없습니다: " + update.getCartItemId());
            }

            if (update.getQuantity() <= 0) {
                cartItemRepository.delete(cartItem); // 수량 0 이하면 삭제 처리
            } else {
                cartItem.setQuantity(update.getQuantity());
            }
        }
    }

    // 장바구니 병합
    @Transactional
    public void syncGuestCart(Long userId, List<GuestCartItemDto> guestItems) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        for (GuestCartItemDto guestItem : guestItems) {
//            if (guestItem.getQuantity() <= 0) {
//                continue; // 수량이 0 이하인 경우 무시
//            }

            ItemEntity item = itemRepository.findById(guestItem.getItemPk())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

//            // 재고 체크 예시 (옵션)
//            if (guestItem.getQuantity() > item.getItemInventory()) {
//                throw new IllegalArgumentException("상품 재고가 부족합니다: " + item.getItemName());
//            }

            Optional<CartItem> existingCartItem = cartItemRepository.findByUserAndItem(user, item);

            if (existingCartItem.isPresent()) {
                CartItem cartItem = existingCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + guestItem.getQuantity());
                // JPA 영속성 컨텍스트 덕분에 따로 save() 호출 불필요
            } else {
                CartItem newCartItem = new CartItem(user, item, guestItem.getQuantity());
                newCartItem.setAddedAt(LocalDateTime.now());
                cartItemRepository.save(newCartItem);
            }
        }
    }





}
