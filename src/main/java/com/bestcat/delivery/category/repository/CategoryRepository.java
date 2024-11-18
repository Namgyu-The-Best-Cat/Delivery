package com.bestcat.delivery.category.repository;

import com.bestcat.delivery.category.entity.Category;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByCategoryNameAndCategoryId(String categoryName, UUID categoryId);

    List<Category> findByCategoryName(String categoryName);

    List<Category> findByCategoryId(UUID categoryId);
}
