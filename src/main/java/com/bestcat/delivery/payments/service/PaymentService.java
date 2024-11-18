package com.bestcat.delivery.payments.service;

import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.common.type.ErrorCode;
import com.bestcat.delivery.order.entity.Order;
import com.bestcat.delivery.order.repository.OrderRepository;
import com.bestcat.delivery.payments.dto.PaymentRequest;
import com.bestcat.delivery.payments.dto.PaymentResponse;
import com.bestcat.delivery.payments.dto.UpdatePaymentStatusRequest;
import com.bestcat.delivery.payments.entity.Payments;
import com.bestcat.delivery.payments.entity.type.PaymentAction;
import com.bestcat.delivery.payments.entity.type.PaymentMethod;
import com.bestcat.delivery.payments.entity.type.PaymentStatus;
import com.bestcat.delivery.payments.repository.PaymentRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    /**
     * 결제 생성
     */
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RestApiException(ErrorCode.ORDER_NOT_FOUND));

        // 카드 결제만 허용
        if (request.paymentMethod() != PaymentMethod.CARD) {
            throw new RestApiException(ErrorCode.INVALID_PAYMENT_METHOD);
        }

        Payments payment = Payments.builder()
                .order(order)
                .paymentMethod(request.paymentMethod())
                .paymentStatus(PaymentStatus.PENDING) // PG사 응답 전 상태
                .paymentAction(PaymentAction.CREATE)
                .amount(request.amount())
                .pgTransactionId(request.pgTransactionId())
                .build();

        Payments savedPayment = paymentRepository.save(payment);

        return PaymentResponse.fromEntity(savedPayment);
    }

    /**
     * 결제 내역 상세 조회
     */
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentDetails(UUID paymentId) {
        Payments payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.PAYMENT_NOT_FOUND));
        return PaymentResponse.fromEntity(payment);
    }

    /**
     * 결제 상태 업데이트
     */
    @Transactional
    public PaymentResponse updatePaymentStatus(UUID paymentId, UpdatePaymentStatusRequest request) {
        Payments payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.PAYMENT_NOT_FOUND));

        payment.updateStatus(request.status()); // 상태 업데이트
        Payments updatedPayment = paymentRepository.save(payment);

        return PaymentResponse.fromEntity(updatedPayment);
    }

}
