package com.bestcat.delivery.user.repository;

import com.bestcat.delivery.user.entity.DeliveryAddress;
import com.bestcat.delivery.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, UUID> {
    Optional<DeliveryAddress> findByUser(User user);

    Optional<DeliveryAddress> findByUserAndDeletedAtIsNull(User user);
}
