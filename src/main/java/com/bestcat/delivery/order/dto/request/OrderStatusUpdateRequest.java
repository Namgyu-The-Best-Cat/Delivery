package com.bestcat.delivery.order.dto.request;

public record OrderStatusUpdateRequest(
        String status, // RECEIVED, CANCELLED, COMPLETED
        String updatedBy
) {
}
