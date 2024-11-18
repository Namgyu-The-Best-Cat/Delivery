package com.bestcat.delivery.payments.dto;

import com.bestcat.delivery.payments.entity.type.PaymentStatus;

public record UpdatePaymentStatusRequest(
        PaymentStatus status
) {
}
