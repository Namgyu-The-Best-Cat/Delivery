package com.bestcat.delivery.order.web;

import static com.bestcat.delivery.common.type.ResponseMessage.*;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.type.*;
import com.bestcat.delivery.order.dto.request.OrderCancelRequest;
import com.bestcat.delivery.order.dto.request.OrderCreationRequest;
import com.bestcat.delivery.order.dto.request.OrderStatusUpdateRequest;
import com.bestcat.delivery.order.dto.response.OrderCancelResponse;
import com.bestcat.delivery.order.dto.response.OrderResponse;
import com.bestcat.delivery.order.service.OrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문생성
    @PostMapping
    public ResponseEntity<SuccessResponse<OrderResponse>> createOrder(
            @RequestBody OrderCreationRequest request,
            @AuthenticationPrincipal UUID userId) {
        OrderResponse orderResponse = orderService.createOrder(request, userId);
        return ResponseEntity.ok(SuccessResponse.of(ORDER_CREATE_SUCCESS, orderResponse));
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<SuccessResponse<OrderResponse>> getOrderDetail(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID orderId) {
        OrderResponse orderResponse = orderService.getOrder(userId, orderId);
        return ResponseEntity.ok(SuccessResponse.of(ORDER_DETAIL_SUCCESS, orderResponse));
    }

    // 주문 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<SuccessResponse<List<OrderResponse>>> getOrderList(
            @AuthenticationPrincipal UUID userId) {
        List<OrderResponse> orderList = orderService.getOrderList(userId);
        return ResponseEntity.ok(SuccessResponse.of(ORDER_LIST_SUCCESS, orderList));
    }

    //주문 상태 변경
    @PostMapping("{orderId}/status")
    public ResponseEntity<SuccessResponse<OrderResponse>> changeOrderStatus(
            @RequestBody OrderStatusUpdateRequest request,
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID orderId) {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId, userId, request);
        return ResponseEntity.ok(SuccessResponse.of(ORDER_STATUS_CHANGE_SUCCESS, orderResponse));
    }

    // 주문 취소
    @PostMapping("{orderId}/cancel")
    public ResponseEntity<SuccessResponse<OrderCancelResponse>> cancelOrder(
            @RequestBody OrderCancelRequest request,
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID orderId) {
        OrderCancelResponse orderCancelResponse = orderService.cancelOrder(orderId, userId,
                request);
        return ResponseEntity.ok(SuccessResponse.of(ORDER_CANCEL_SUCCESS, orderCancelResponse));
    }

}
