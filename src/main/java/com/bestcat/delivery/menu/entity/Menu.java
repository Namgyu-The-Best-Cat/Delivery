package com.bestcat.delivery.menu.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.menu.dto.MenuRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_menu")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @Column(name = "store_id")
    private Store store;

    @ManyToOne
    @Column(name = "category_id")
    private Category category;

    private String name;

    private Integer price;

    private String photoUrl;

    private String description;

    public void update(MenuRequestDto requestDto){

    }
}
