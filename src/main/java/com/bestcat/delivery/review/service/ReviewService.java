package com.bestcat.delivery.review.service;

import com.bestcat.delivery.review.dto.ReviewResponseDto;
import com.bestcat.delivery.review.dto.ReviewRequestDto;
import com.bestcat.delivery.review.entity.Review;
import com.bestcat.delivery.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getStoreReview(String storeId, int page, int limit, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Review> reviewList;

        reviewList = reviewRepository.findAllByOrderStoreId(storeId, pageable);

        return reviewList.map(ReviewResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getUserReview(String userId, int page, int limit, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Review> reviewList;

        reviewList = reviewRepository.findAllByUserUserId(userId, pageable);

        return reviewList.map(ReviewResponseDto::new);
    }

    public ReviewResponseDto createReview(ReviewRequestDto requestDto/*, UUID userId*/) {
        Review review = Review.builder()
//                .user(userRepository.findById(userId))
                .order(orderRepository.findById(requestDto.orderId()))
                .content(requestDto.content())
                .rating(requestDto.rating())
                .build();

        reviewRepository.save(review);
        return new ReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto updateReview(UUID id, ReviewRequestDto requestDto) {

        if (requestDto.content().isEmpty()) throw new IllegalArgumentException("본문이 비어있습니다. 리뷰 내용을 입력해주세요.");

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 리뷰를 찾을 수 없습니다."));

        review.update(requestDto);
        reviewRepository.save(review);
        return new ReviewResponseDto(review);
    }

    public ReviewResponseDto deleteReview(UUID id/*, UUID userId*/) {

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 리뷰를 찾을 수 없습니다."));

        review.delete(userId);
        reviewRepository.save(review);
        return new ReviewResponseDto(review);
    }
}
