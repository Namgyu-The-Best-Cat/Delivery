package com.bestcat.delivery.category.entity;

import com.bestcat.delivery.category.dto.CategoryRequestDto;
import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.store.entity.StoreCategory;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="p_category")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", updatable = false, nullable = false)
    private UUID categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoreCategory> storeCategories = new HashSet<>();

    public void update(@Valid CategoryRequestDto categoryRequestDto) {
        if (categoryRequestDto.categoryName() != null) {
            this.categoryName = categoryRequestDto.categoryName();
        }
    }
}