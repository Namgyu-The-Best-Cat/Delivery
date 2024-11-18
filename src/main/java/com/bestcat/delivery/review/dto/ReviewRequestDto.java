package com.bestcat.delivery.review.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record ReviewRequestDto(
        UUID orderId,
        String content,
        int rating,
        List<MultipartFile> file
) {

}
