package com.bestcat.delivery.review.dto;

import com.bestcat.delivery.review.entity.Review;
import com.bestcat.delivery.review.entity.ReviewPhoto;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ReviewResponseDto(
        UUID id,
        UUID username,
        UUID orderId,
        String content,
        Integer rating,
        Timestamp createAt,
        UUID createBy,
        List<String> photoUrl
){
    public static ReviewResponseDto from(Review review) {

        List<String> photoUrls = review.getPhotos().stream()
                .map(ReviewPhoto::getUrl)
                .toList();

        return new ReviewResponseDto(
                review.getId(),
                review.getUser().getId(),
//                review.getOrder().getOrderId(),
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt(),
                review.getCreatedBy(),
                photoUrls
        );
    }
}
