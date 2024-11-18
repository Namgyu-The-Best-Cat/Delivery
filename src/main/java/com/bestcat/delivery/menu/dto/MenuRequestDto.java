package com.bestcat.delivery.menu.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record MenuRequestDto(
        String name,
        int price,
        UUID storeId,
        UUID categoryId,
        MultipartFile photo,
        String description
) {

}
