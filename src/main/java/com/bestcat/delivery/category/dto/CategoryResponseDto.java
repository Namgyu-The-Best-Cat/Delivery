package com.bestcat.delivery.category.dto;

import com.bestcat.delivery.category.entity.Category;

import java.util.UUID;

public record CategoryResponseDto(
        UUID categoryId,
        String categoryName
) {

    public static CategoryResponseDto fromCategory(Category category) {
        return new CategoryResponseDto(
                category.getCategoryId(),
                category.getCategoryName()
        );
    }

}
