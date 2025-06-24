package net.supercoding.backend.domain.user.service.payment;

import net.supercoding.backend.domain.user.dto.payment.CreatePaymentRequestDto;
import net.supercoding.backend.domain.user.dto.payment.PaymentResponseDto;
import net.supercoding.backend.domain.user.dto.payment.PaymentSimpleDto;
import net.supercoding.backend.domain.user.entity.User;

import java.util.List;

public interface PaymentService {
    PaymentResponseDto createPayment(User user, List<CreatePaymentRequestDto.PaymentItemRequestDto> items);
    List<PaymentSimpleDto> getPaymentHistory(User user);
    PaymentResponseDto getPaymentDetail(User user, Long paymentPk);

    void deletePaymentsByUserId(Long userId);
}
