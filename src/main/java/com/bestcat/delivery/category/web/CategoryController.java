package com.bestcat.delivery.category.web;

import com.bestcat.delivery.category.dto.CategoryRequestDto;
import com.bestcat.delivery.category.dto.CategoryResponseDto;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    public void createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        categoryService.save(categoryRequestDto);

    }

    @GetMapping("/categories")
    public List<CategoryResponseDto> searchCategory(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) UUID categoryId) {
        return categoryService.searchAreas(categoryName, categoryId);
    }

    @PutMapping("/categories/{categoryId}")
    public void updateCategory(@PathVariable UUID categoryId, @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.updateCategory(categoryId, categoryRequestDto);
    }


}
