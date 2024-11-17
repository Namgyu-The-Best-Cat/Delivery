package com.bestcat.delivery.menu.entity;

import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_menu")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private Integer price;

    private String photoUrl;

    private String description;

    public void update(Category category, String name, int price, String photoUrl, String description) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.photoUrl = photoUrl;
        this.description = description;
    }
}
