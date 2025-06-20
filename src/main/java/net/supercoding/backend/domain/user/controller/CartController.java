package net.supercoding.backend.domain.user.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.cart.*;
import net.supercoding.backend.domain.user.security.CustomUserDetails;
import net.supercoding.backend.domain.user.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<AddToCartResponseDto> addToCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody AddToCartRequestDto requestDto
    ) {
        AddToCartResponseDto responseDto = cartService.addToCart(
                userDetails.getUser(),
                requestDto.getItemPk(),
                requestDto.getQuantity()
        );
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<CartItemResponseDto> cartItems = cartService.getCartItems(userDetails.getUser());
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping
    public ResponseEntity<Void> updateCartItemsQuantities(
            @RequestBody CartItemsQuantityUpdateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        cartService.updateQuantities(userDetails.getUser(), requestDto.getItems());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartPk}")
    public ResponseEntity<Void> deleteCartItem(
            @PathVariable Long cartPk,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        cartService.deleteCartItem(userDetails.getUser(), cartPk);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @PostMapping("/sync")
    public ResponseEntity<List<CartItemResponseDto>> syncCartItems(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CartSyncRequestDto requestDto) {

        List<CartItemResponseDto> updatedCart = cartService.syncCartItems(userDetails.getUser(), requestDto.getItems());
        return ResponseEntity.ok(updatedCart);
    }



}
