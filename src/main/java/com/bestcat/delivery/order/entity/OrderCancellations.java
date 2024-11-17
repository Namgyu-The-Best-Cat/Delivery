package com.bestcat.delivery.order.entity;


import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.user.entity.User;
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
@Table(name = "p_order_cancellations")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCancellations extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name ="cancellation_id",updatable = false, nullable = false)
    private UUID cancellationId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;

    @Column(name = "cancel_reason", nullable = false)
    private String cancelReason;


}
