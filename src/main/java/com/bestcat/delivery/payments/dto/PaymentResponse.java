package com.bestcat.delivery.payments.dto;

import com.bestcat.delivery.payments.entity.Payments;
import com.bestcat.delivery.payments.entity.type.PaymentAction;
import com.bestcat.delivery.payments.entity.type.PaymentMethod;
import com.bestcat.delivery.payments.entity.type.PaymentStatus;

import java.util.UUID;

public record PaymentResponse(
        UUID paymentId,
        UUID orderId,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        PaymentAction paymentAction,
        Integer amount,
        String pgTransactionId
) {
    public static PaymentResponse fromEntity(Payments payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrder().getOrderId(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaymentAction(),
                payment.getAmount(),
                payment.getPgTransactionId()
        );
    }
}
