package com.bestcat.delivery.order.service;

import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.common.type.ErrorCode;
import com.bestcat.delivery.menu.entity.Menu;
import com.bestcat.delivery.order.dto.request.OrderCreationRequest;
import com.bestcat.delivery.order.dto.request.OrderStatusUpdateRequest;
import com.bestcat.delivery.order.dto.response.OrderCancelResponse;
import com.bestcat.delivery.order.dto.response.OrderResponse;
import com.bestcat.delivery.order.entity.Order;
import com.bestcat.delivery.order.entity.OrderCancellations;
import com.bestcat.delivery.order.entity.OrderItems;
import com.bestcat.delivery.order.entity.type.OrderStatus;
import com.bestcat.delivery.order.entity.type.OrderType;
import com.bestcat.delivery.order.repository.OrderRepository;
import com.bestcat.delivery.store.entity.Store;
import com.bestcat.delivery.user.entity.RoleType;
import com.bestcat.delivery.user.entity.User;
import com.bestcat.delivery.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    // private final MenuRepository menuRepository;
    //private final StoreRepository storeRepository;


    /**
     * 주문을 생성하는 메서드
     *
     * @param request 주문 생성 요청 데이터
     * @param userId  주문을 생성하는 사용자 ID
     * @return 생성된 주문 정보(OrderResponse)
     */
    @Transactional
    public OrderResponse createOrder(OrderCreationRequest request, UUID userId) {
        User user = getUser(userId);

//        Store store = storeRepository.findById(request.storeId()).orElseThrow(()
//                -> new RestApiException(ErrorCode.STORE_NOT_FOUND));

        Store 임시코드 = new Store(); // TODO StoreRepository 생성 되면 수정

        Order order = Order.builder()
                .user(user)
                .store(임시코드)
                .orderType(request.orderType())
                .status(OrderStatus.DEFAULT)
                .address(request.address())
                .requestNotes(request.requestNotes())
                .build();

        List<OrderItems> orderItems = request.items().stream().map(itemRequest -> {
            //Menu menu = menuRepository.findById(itemRequest.menuId());
            Menu 임시코드02 = new Menu(); // TODO MenuRepository 생성 되면 수정

            OrderItems item = OrderItems.builder()
                    .menu(임시코드02)
                    .quantity(itemRequest.quantity())
                    .price(임시코드02.getPrice() * itemRequest.quantity())
                    .build();
            order.addOrderItem(item);
            return item;
        }).toList();

        Order savedOrder = orderRepository.save(order);

        // 총 가격 계산
        double totalPrice = getTotalOrderPrice(orderItems);

        return OrderResponse.fromEntity(savedOrder, totalPrice, orderItems);
    }

    /**
     * 주문 상세 조회
     *
     * @param orderId
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public OrderResponse getOrder(UUID orderId, UUID userId) {
        User user = getUser(userId);
        Order order = getOrder(orderId);

        // 요구사항_권한 확인: 고객은 자신의 주문만, 가게 주인은 자신의 가게 주문만 조회 가능
        if (user.getRole().equals(RoleType.CUSTOMER) && !order.getUser().getId().equals(userId)) {
            throw new RestApiException(ErrorCode.UNAUTHORIZED);
        } else if (user.getRole().equals(RoleType.OWNER) && !order.getStore().getOwner().getId()
                .equals(userId)) {
            throw new RestApiException(ErrorCode.UNAUTHORIZED);
        }

        return OrderResponse.fromEntity(order, getTotalOrderPrice(order.getOrderItems()),
                order.getOrderItems());
    }

    /**
     * 주문 리스트 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(UUID userId) {
        User user = getUser(userId);
        List<Order> orders;
        if (user.getRole().equals(RoleType.CUSTOMER)) {
            orders = orderRepository.findByUserId(user.getId());
        } else if (user.getRole().equals(RoleType.OWNER)) {
            orders = orderRepository.findByStoreOwnerId(userId);
        } else {
            throw new RestApiException(ErrorCode.INVALID_ROLE);
        }

        return orders.stream()
                .map(order -> {
                    double totalPrice = getTotalOrderPrice(order.getOrderItems());

                    return OrderResponse.fromEntity(order, totalPrice, order.getOrderItems());
                }).toList();

    }

    /**
     * 주문 취소
     *
     * @param orderId
     * @param userId
     * @param cancelReason
     * @return
     */
    @Transactional
    public OrderCancelResponse cancelOrder(UUID orderId, UUID userId, String cancelReason) {
        Order order = getOrder(orderId);

        // 요구사항_권한 확인: 고객만 자신의 주문을 취소할 수 있음
        if (!order.getUser().getId().equals(userId)) {
            throw new RestApiException(ErrorCode.UNAUTHORIZED);
        }

        // 요구사항_RECEIVED 상태에서만 취소 가능
        if (order.getStatus() != OrderStatus.RECEIVED) {
            throw new RestApiException(ErrorCode.INVALID_ORDER_STATUS);
        }

        order.cancelOrder();
        OrderCancellations orderCancellations = OrderCancellations.builder()
                .order(order)
                .cancelReason(cancelReason)
                .build();

        orderRepository.save(order);

        return OrderCancelResponse.fromEntity(orderCancellations);
    }

    /**
     * 주문 상태 변경
     *
     * @param orderId
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public OrderResponse updateOrderStatus(UUID orderId, UUID userId,
                                           OrderStatusUpdateRequest request) {
        Order order = getOrder(orderId);

        // 요구사항_권한 확인: 가게 주인만 상태 변경 가능
        if (!order.getStore().getOwner().getId().equals(userId)) {
            throw new RestApiException(ErrorCode.UNAUTHORIZED);
        }

        order.updateOrderStatus(request.status());

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.fromEntity(savedOrder,
                getTotalOrderPrice(savedOrder.getOrderItems()), savedOrder.getOrderItems());

    }


    private Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RestApiException(ErrorCode.ORDER_NOT_FOUND));
    }

    private static double getTotalOrderPrice(List<OrderItems> orderItems) {
        return orderItems.stream().mapToDouble(OrderItems::getPrice).sum();
    }

    private User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new RestApiException(ErrorCode.USER_NOT_FOUND));
    }


}
