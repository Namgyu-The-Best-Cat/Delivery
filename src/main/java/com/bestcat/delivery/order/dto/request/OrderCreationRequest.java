package com.bestcat.delivery.order.dto.request;

import java.util.List;
import java.util.UUID;

public record OrderCreationRequest(
        UUID storeId,
        String orderType, // ONLINE, IN_PERSON
        String address,
        String requestNotes,
        List<OrderItemRequest> items

) {
}
