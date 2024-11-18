package com.bestcat.delivery.order.dto.request;

import com.bestcat.delivery.order.entity.type.OrderType;
import java.util.List;
import java.util.UUID;

public record OrderCreationRequest(
        UUID storeId,
        OrderType orderType, // ONLINE, IN_PERSON
        String address,
        String requestNotes,
        List<OrderItemRequest> items
) {
}
