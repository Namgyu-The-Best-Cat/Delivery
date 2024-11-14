package com.bestcat.delivery.order.service;

import static java.awt.SystemColor.menu;

import com.bestcat.delivery.common.dto.ErrorResponse;
import com.bestcat.delivery.common.exception.GlobalExceptionHandler;
import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.common.type.ErrorCode;
import com.bestcat.delivery.menu.entity.Menu;
import com.bestcat.delivery.order.dto.request.OrderCreationRequest;
import com.bestcat.delivery.order.dto.response.OrderItemResponse;
import com.bestcat.delivery.order.dto.response.OrderResponse;
import com.bestcat.delivery.order.entity.Order;
import com.bestcat.delivery.order.entity.OrderItems;
import com.bestcat.delivery.order.entity.type.OrderStatus;
import com.bestcat.delivery.order.entity.type.OrderType;
import com.bestcat.delivery.order.repository.OrderRepository;
import com.bestcat.delivery.store.entity.Store;
import com.bestcat.delivery.user.entity.User;
import com.bestcat.delivery.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
   // private final MenuRepository menuRepository;
    //private final StoreRepository storeRepository;

    public OrderResponse createOrder(OrderCreationRequest request, UUID userId){
       User user = userRepository.findById(userId).orElseThrow(()
               -> new RestApiException(ErrorCode.USER_NOT_FOUND));

//        Store store = storeRepository.findById(request.storeId()).orElseThrow(()
//                -> new RestApiException(ErrorCode.STORE_NOT_FOUND));

        Store 임시코드 = new Store(); // TODO StoreRepository 생성 되면 수정

        Order order = Order.builder()
                .user(user)
                .store(임시코드)
                .orderType(OrderType.valueOf(request.orderType()))
                .status(OrderStatus.RECEIVED)
                .address(request.address())
                .requestNotes(request.requestNotes())
                .build();
        List<OrderItems> orderItems = request.items().stream().map(itemRequest -> {
            //Menu menu = menuRepository.findById(itemRequest.menuId());
            Menu 임시코드02 = new Menu();
            return OrderItems.builder()
                    .order(order)
                    .menu(임시코드02)
                    .quantity(itemRequest.quantity())
                    .price(임시코드02.getPrice() * itemRequest.quantity())
                    .build();
        }).collect(Collectors.toList());

        // 총 가격 계산
        double totalPrice = orderItems.stream()
                .mapToDouble(OrderItems::getPrice)
                .sum();

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.fromEntity(savedOrder, totalPrice, orderItems);
    }


}
