package com.bestcat.delivery.order.repository;

import com.bestcat.delivery.order.entity.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("select o from Order o join fetch o.user u where u.id = :userId")
    List<Order> findByUserId(@Param("userId") UUID userId);

    @Query("select o from Order o join o.store s where s.owner.id = :ownerId")
    List<Order> findByStoreOwnerId(@Param("ownerId") UUID ownerId);
}
