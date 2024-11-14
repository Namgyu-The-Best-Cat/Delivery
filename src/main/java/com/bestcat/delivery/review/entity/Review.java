package com.bestcat.delivery.review.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.review.dto.ReviewRequestDto;
import com.bestcat.delivery.order.entity.Order;
import com.bestcat.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "p_review")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String content;
    private Integer rating;

    public void update(ReviewRequestDto requestDto){
        this.content = requestDto.content();
        this.rating = requestDto.rating();
    }
}

