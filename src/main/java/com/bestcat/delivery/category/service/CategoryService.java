package com.bestcat.delivery.category.service;

import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.category.dto.CategoryRequestDto;
import com.bestcat.delivery.category.dto.CategoryResponseDto;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.category.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void save(CategoryRequestDto requestDto) {
        categoryRepository.save(requestDto.toEntity());
    }

    public List<CategoryResponseDto> searchAreas(String categoryName, UUID categoryId) {
        List<Category> categories;
        if (categoryName != null && categoryId != null) {
            categories = categoryRepository.findByCategoryNameAndCategoryId(categoryName, categoryId);
        } else if (categoryName != null) {
            categories = categoryRepository.findByCategoryName(categoryName);
        } else if (categoryId != null) {
            categories = categoryRepository.findByCategoryId(categoryId);
        } else {
            categories = categoryRepository.findAll();
        }

        return categories.stream()
                .map(CategoryResponseDto::fromCategory)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCategory(UUID categoryId, @Valid CategoryRequestDto categoryRequestDto) {
        Category updateCategory = categoryRepository.findById(categoryId).orElse(null);
        if (updateCategory != null) {
            updateCategory.update(categoryRequestDto);
        }
    }
}
