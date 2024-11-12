package com.bestcat.delivery.review.repository;

import com.bestcat.delivery.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findAllByOrderStoreId(String storeId, Pageable pageable);

    Page<Review> findAllByUserUserId(String userId, Pageable pageable);
}
