package com.bestcat.delivery.payments.dto;

import com.bestcat.delivery.payments.entity.type.PaymentMethod;
import java.util.UUID;
public record PaymentRequest(
        UUID orderId,
        PaymentMethod paymentMethod,
        Integer amount,
        String pgTransactionId
) {
}
