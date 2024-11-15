package com.bestcat.delivery.review.repository;

import com.bestcat.delivery.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findAllByOrderStoreStoreIdAndDeleteAtIsNull(UUID storeId, Pageable pageable);

    Page<Review> findAllByUserIdAndDeleteAtIsNull(UUID userId, Pageable pageable);
}
