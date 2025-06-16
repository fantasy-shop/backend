package net.supercoding.backend.domain.user.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.dto.payment.CreatePaymentRequestDto;
import net.supercoding.backend.domain.user.dto.payment.PaymentResponseDto;
import net.supercoding.backend.domain.user.dto.payment.PaymentSimpleDto;
import net.supercoding.backend.domain.user.security.CustomUserDetails;
import net.supercoding.backend.domain.user.service.payment.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreatePaymentRequestDto requestDto
    ) {
        PaymentResponseDto response = paymentService.createPayment(userDetails.getUser(), requestDto.getItems());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentSimpleDto>> getPayments(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(paymentService.getPaymentHistory(userDetails.getUser()));
    }

    @GetMapping("/{paymentPk}")
    public ResponseEntity<PaymentResponseDto> getPaymentDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long paymentPk
    ) {
        return ResponseEntity.ok(paymentService.getPaymentDetail(userDetails.getUser(), paymentPk));
    }
}

