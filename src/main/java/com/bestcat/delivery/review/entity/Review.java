package com.bestcat.delivery.review.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "p_review")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @Column(name = "user_id")
    private User user;

    @ManyToOne
    @Column(name = "order_id")
    private Order order;

    private String content;
    private Integer rating;

    private Timestamp delete_at;
    @ManyToOne
    private User delete_by;
}
