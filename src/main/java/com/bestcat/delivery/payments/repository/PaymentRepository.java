package com.bestcat.delivery.payments.repository;

import com.bestcat.delivery.payments.entity.Payments;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payments, UUID> {
}
