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
    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.username = review.getUser().getUsername();
        this.orderId = review.getOrder().getOrderId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createAt = review.getCreatedAt();
        this.createBy = review.getCreatedBy();
    }
}
