package com.bestcat.delivery.order.dto.response;

import com.bestcat.delivery.order.entity.OrderCancellations;
import java.util.UUID;

public record OrderCancelResponse(
        UUID cancellationId,
        UUID userId,
        UUID orderId,
        String cancelReason
) {

    public static OrderCancelResponse fromEntity(OrderCancellations orderCancellations) {
        return new OrderCancelResponse(
                orderCancellations.getCancellationId(),
                orderCancellations.getUser().getId(),
                orderCancellations.getOrder().getOrderId(),
                orderCancellations.getCancelReason());
    }
}
