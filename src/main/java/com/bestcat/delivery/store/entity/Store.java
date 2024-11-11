package com.bestcat.delivery.store.entity;

import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.category.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "p_store")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @UuidGenerator
    private UUID store_id;

    private String store_name;

    @ManyToOne
    @Column(name = "userId")
    private Users owner;

    @ManyToOne
    @Column(name = "category_id")
    private Category category;

    private String store_phone;

    @ManyToOne
    @Column(name="area_id")
    private Area area;

    private String district;

    private String address_detail;

    private Integer total_reviews;

    private Integer total_stars;



}
