package net.supercoding.backend.domain.user.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.cart.*;
import net.supercoding.backend.domain.user.security.CustomUserDetails;
import net.supercoding.backend.domain.user.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
// import 커밋용 주석입니다.
import java.util.List;

@RestController // REST API 컨트롤러 (응답을 JSON으로 반환)
@RequiredArgsConstructor // fianl 필드에 자동 생성자 주입
@RequestMapping("/cart") // 모든 경로가 "/cart"로 시작
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


    // 장바구니 비회원과 회원 병합
    @PostMapping("/sync")
    public ResponseEntity<?> syncGuestCart(@RequestBody GuestCartSyncRequestDto requestDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getUserPk();
        cartService.syncGuestCart(userId, requestDto.getItems());

        List<CartItemResponseDto> updatedCart = cartService.getCartItems(userDetails.getUser());
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(userDetails.getUser(), cartItemId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
