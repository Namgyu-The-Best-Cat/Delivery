package com.bestcat.delivery.order.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.menu.entity.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order_items")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="order_id",updatable = false, nullable = false)
    private UUID orderItemsId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Integer quantity; // 메뉴 수량

    @Column(nullable = false, precision = 10, scale = 2)
    private Integer price; // 메뉴 가격

}
