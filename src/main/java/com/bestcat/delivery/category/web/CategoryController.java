package com.bestcat.delivery.category.web;

import com.bestcat.delivery.category.dto.CategoryRequestDto;
import com.bestcat.delivery.category.dto.CategoryResponseDto;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.category.service.CategoryService;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Secured({"ROLE_MASTER"})
    @PostMapping("/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.save(categoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Category가 추가되었습니다: " + categoryRequestDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryResponseDto>> searchCategory(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<CategoryResponseDto> categories = categoryService.searchCategories(categoryName, categoryId, page, size);
        return ResponseEntity.ok(categories);
    }

    @Secured({"ROLE_MASTER"})
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable UUID categoryId,
                                                 @Valid @RequestBody CategoryRequestDto categoryRequestDto) {

        categoryService.updateCategory(categoryId, categoryRequestDto);

        return ResponseEntity.ok("Category가 성공적으로 업데이트되었습니다: " + categoryId);
    }

    @Secured({"ROLE_MASTER"})
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID categoryId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {

        categoryService.deleteCategory(categoryId, userDetails.getUserId());

        return ResponseEntity.ok("Category가 성공적으로 삭제되었습니다: " + categoryId);
    }
}