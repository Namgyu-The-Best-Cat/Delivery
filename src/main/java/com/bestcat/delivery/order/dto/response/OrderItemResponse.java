package com.bestcat.delivery.order.dto.response;

import com.bestcat.delivery.menu.entity.Menu;
import com.bestcat.delivery.order.entity.OrderItems;
import java.util.UUID;

public record OrderItemResponse(
        UUID orderItemId,
        UUID menuId,
        int quantity,
        double price
) {
    public static OrderItemResponse fromEntity(OrderItems items) {
        return new OrderItemResponse(
                items.getOrderItemsId(),
                items.getMenu().getId(),
                items.getQuantity(),
                items.getPrice()
        );
    }
}
