package com.bestcat.delivery.menu.dto;


import java.util.UUID;

public record MenuRequestDto(
    UUID id,
    UUID storeId,
    String name,
    int price,
    UUID categoryId,
    String photoUrl,
    String description
) {
}
