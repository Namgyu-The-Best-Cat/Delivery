package com.bestcat.delivery.review.dto;

import com.bestcat.delivery.review.entity.Review;

import java.sql.Timestamp;
import java.util.UUID;

public record ReviewResponseDto(
        UUID id,
        UUID username,
        UUID orderId,
        String content,
        Integer rating,
        Timestamp createAt,
        UUID createBy
){
    public static ReviewResponseDto from(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getUser().getId(),
                review.getOrder().getOrderId(),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt(),
                review.getCreatedBy()
        );
    }
}
