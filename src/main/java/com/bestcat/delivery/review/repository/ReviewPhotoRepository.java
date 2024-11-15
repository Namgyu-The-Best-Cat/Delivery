package com.bestcat.delivery.review.repository;


import com.bestcat.delivery.review.entity.ReviewPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, UUID> {
}
