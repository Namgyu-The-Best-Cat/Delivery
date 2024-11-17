package com.bestcat.delivery.order.dto.request;

import com.bestcat.delivery.order.entity.type.OrderStatus;

public record OrderStatusUpdateRequest(
        OrderStatus status, // RECEIVED, CANCELLED, COMPLETED
        String updatedBy
) {
}
