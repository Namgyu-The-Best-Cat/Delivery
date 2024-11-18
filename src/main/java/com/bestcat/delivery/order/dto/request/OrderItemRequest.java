package com.bestcat.delivery.order.dto.request;

import java.util.UUID;

public record OrderItemRequest(
        UUID menuId,
        int quantity
) {
}
