package com.bestcat.delivery.order.dto.response;

import com.bestcat.delivery.order.entity.Order;
import com.bestcat.delivery.order.entity.OrderItems;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        UUID userId,
        UUID storeId,
        String orderType,
        String status,
        String address,
        String requestNotes,
        Timestamp createdAt,
        Timestamp updatedAt,
        double totalPrice, // 총 가격
        List<OrderItems> items
) {
    public static OrderResponse fromEntity(Order order, double totalPrice, List<OrderItems> items) {
        return new OrderResponse(
                order.getOrderId(),
                order.getUser().getId(),
                order.getStore().getStoreId(),
                order.getOrderType().name(),
                order.getStatus().name(),
                order.getAddress(),
                order.getRequestNotes(),
                order.getCreatedAt(),
                order.getUpdateAt(),
                totalPrice,
                items
        );
    }
}
