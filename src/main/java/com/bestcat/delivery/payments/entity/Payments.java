package com.bestcat.delivery.payments.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.order.entity.Order;
import com.bestcat.delivery.payments.entity.type.PaymentAction;
import com.bestcat.delivery.payments.entity.type.PaymentMethod;
import com.bestcat.delivery.payments.entity.type.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_payments")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", updatable = false, nullable = false)
    private UUID paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_action", nullable = false)
    private PaymentAction paymentAction;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private Integer amount;

    @Column(name = "pg_transaction_id", nullable = false, length = 100)
    private String pgTransactionId;

    public void updateStatus(PaymentStatus status) {
        this.paymentStatus = status;
    }
}
