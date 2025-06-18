package net.supercoding.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.entity.ItemEntity;
import net.supercoding.backend.domain.item.repository.ItemRepository;
import net.supercoding.backend.domain.user.dto.cart.AddToCartResponseDto;
import net.supercoding.backend.domain.user.dto.cart.CartItemQuantityUpdateRequestDto;
import net.supercoding.backend.domain.user.dto.cart.CartItemResponseDto;
import net.supercoding.backend.domain.user.entity.CartItem;
import net.supercoding.backend.domain.user.entity.User;
import net.supercoding.backend.domain.user.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public AddToCartResponseDto addToCart(User user, Long itemId, int quantity) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        CartItem cartItem = cartItemRepository.findByUserAndItem(user, item)
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    return existingItem;
                })
                .orElseGet(() -> cartItemRepository.save(new CartItem(user, item, quantity)));

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



}
