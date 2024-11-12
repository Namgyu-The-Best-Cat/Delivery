package com.bestcat.delivery.store.entity;

import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "p_store")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id", updatable = false, nullable = false)
    private UUID storeId;

    @Column(name="store_name")
    private String storeName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users owner;


    @Column(name="store_phone")
    private String storePhone;

    @ManyToOne
    @JoinColumn (name="area_id")
    private Area area;

    @Column(name="district")
    private String district;

    @Column(name ="address_detail" )
    private String addressDetail;

    @Column(name="total_reviews")
    private Integer totalReviews;

    @Column(name="total_stars")
    private Integer totalStars;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoreCategory> storeCategories = new HashSet<>();

}