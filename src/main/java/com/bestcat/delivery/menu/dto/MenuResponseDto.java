package com.bestcat.delivery.menu.dto;

import com.bestcat.delivery.menu.entity.Menu;

import java.util.UUID;

public record MenuResponseDto(
        UUID id,
        UUID storeId,
        String name,
        int price,
        UUID categoryId,
        String photoUrl,
        String description
) {
    public static MenuResponseDto from(Menu menu) {
        return new MenuResponseDto(
                menu.getId(),
                menu.getStore().getStoreId(),
                menu.getName(),
                menu.getPrice(),
                menu.getCategory().getCategoryId(),
                menu.getPhotoUrl(),
                menu.getDescription()
        );
    }
}
