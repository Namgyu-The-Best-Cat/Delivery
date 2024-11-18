package com.bestcat.delivery.category.service;

import com.bestcat.delivery.area.dto.AreaResponseDto;
import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.category.dto.CategoryRequestDto;
import com.bestcat.delivery.category.dto.CategoryResponseDto;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public Page<CategoryResponseDto> searchCategories(String categoryName, UUID categoryId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));

        Specification<Category> specification = createSpecification(categoryName, categoryId);

        Page<Category> categories = categoryRepository.findAll(specification, pageable);

        return categories.map(CategoryResponseDto::fromCategory);
    }

    private Specification<Category> createSpecification(String categoryName, UUID categoryId) {
        Specification<Category> spec = Specification.where(isNotDeleted());

        // 조건 추가
        if (categoryName != null && !categoryName.isEmpty()) {
            spec = spec.and(categoryNameLike(categoryName));
        }
        if (categoryId != null) {
            spec = spec.and(categoryIdEquals(categoryId));
        }

        return spec;
    }

    private Specification<Category> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    private Specification<Category> categoryNameLike(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("categoryName"), "%" + categoryName + "%");
    }

    private Specification<Category> categoryIdEquals(UUID categoryId) {
        if (categoryId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryId"), categoryId);
    }

    @Transactional
    public void updateCategory(UUID categoryId, @Valid CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(categoryId + " 값을 갖는 category가 없습니다."));

        category.update(categoryRequestDto);
    }

    @Transactional
    public void deleteCategory(UUID categoryId, UUID userId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 category가 존재하지 않습니다."));
        category.delete(userId);
    }
}
