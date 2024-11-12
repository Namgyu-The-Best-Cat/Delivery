package com.bestcat.delivery.review.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ReviewRequestDto(
        UUID orderId,
        String content,
        int rating,
        MultipartFile file
) {

}
