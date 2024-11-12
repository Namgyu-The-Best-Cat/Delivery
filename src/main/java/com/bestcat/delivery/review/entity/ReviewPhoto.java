package com.bestcat.delivery.review.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "p_review_photo")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPhoto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @Column(name = "review_id")
    private Review review;

    private String url;
}
