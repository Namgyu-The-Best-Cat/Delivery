package com.bestcat.delivery.category.dto;

import com.bestcat.delivery.category.entity.Category;

import javax.swing.text.html.parser.Entity;
import java.util.UUID;

public record CategoryRequestDto(
        UUID categoryId,
        String categoryName
) {
    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
}
