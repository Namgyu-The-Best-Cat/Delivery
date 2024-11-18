package com.bestcat.delivery.store.entity;

import com.bestcat.delivery.area.entity.Area;
import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.review.entity.Review;
import com.bestcat.delivery.store.dto.StoreRequestDto;
import com.bestcat.delivery.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "p_store")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id", updatable = false, nullable = false)
    private UUID storeId;

    @Column(name = "store_name")
    private String storeName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;


    @Column(name = "store_phone")
    private String storePhone;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "district")
    private String district;

    @Column(name = "address_detail")
    private String addressDetail;


    @Column(name = "total_reviews")
    private Integer totalReviews;

    @Column(name = "total_stars")
    private Integer totalStars;

    @Builder.Default
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StoreCategory> storeCategories = new HashSet<>();

    @PrePersist
    public void setDefaultValues() {
        if (this.totalReviews == null) {
            this.totalReviews = 0;
        }
        if (this.totalStars == null) {
            this.totalStars = 0;
        }
    }

    public void addCategory(Category category) {
        StoreCategory storeCategory = new StoreCategory(this, category);
        this.storeCategories.add(storeCategory);
    }

    public void update(@Valid StoreRequestDto storeRequestDto) {
        if (storeRequestDto.storeName() != null) {
            this.storeName = storeRequestDto.storeName();
        }
        if (storeRequestDto.storePhone() != null) {
            this.storePhone = storeRequestDto.storePhone();
        }
        if (storeRequestDto.district() != null) {
            this.district = storeRequestDto.district();
        }
        if (storeRequestDto.addressDetail() != null) {
            this.addressDetail = storeRequestDto.addressDetail();
        }
        if (storeRequestDto.totalReviews() != null) {
            this.totalReviews = storeRequestDto.totalReviews();
        }
        if (storeRequestDto.totalStars() != null) {
            this.totalStars = storeRequestDto.totalStars();
        }
    }

    public void incrementTotalReviews(Review review) {
        this.totalStars += review.getRating();
        this.totalReviews++;
    }

    public void decrementTotalReviews(Review review) {
        this.totalStars -= review.getRating();
        this.totalReviews--;
    }
}