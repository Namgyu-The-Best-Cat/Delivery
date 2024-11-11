package com.bestcat.delivery.category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name="p_category")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @UuidGenerator
    private UUID category;

    private String category_name;

}
