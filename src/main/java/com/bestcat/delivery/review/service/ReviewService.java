package com.bestcat.delivery.review.service;

import com.bestcat.delivery.common.util.FileUpload;
import com.bestcat.delivery.review.dto.ReviewResponseDto;
import com.bestcat.delivery.review.dto.ReviewRequestDto;
import com.bestcat.delivery.review.entity.Review;
import com.bestcat.delivery.review.entity.ReviewPhoto;
import com.bestcat.delivery.review.repository.ReviewPhotoRepository;
import com.bestcat.delivery.review.repository.ReviewRepository;
import com.bestcat.delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
//    private final UserRepository userRepository;
//    private final OrderRepository orderRepository;

    private final FileUpload fileUpload;

    @Transactional(readOnly = true)
    public ResponseEntity<Page<ReviewResponseDto>> getStoreReview(UUID storeId, int page, int limit, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Review> reviewList;

        reviewList = reviewRepository.findAllByOrderStoreStoreIdAndDeletedAtIsNull(storeId, pageable);

        return ResponseEntity.ok().body(reviewList.map(ReviewResponseDto::from));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Page<ReviewResponseDto>> getUserReview(UUID userId, int page, int limit, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Review> reviewList;

        reviewList = reviewRepository.findAllByUserIdAndDeletedAtIsNull(userId, pageable);

        return ResponseEntity.ok().body(reviewList.map(ReviewResponseDto::from));
    }

    public ResponseEntity<ReviewResponseDto> createReview(ReviewRequestDto requestDto, User user) {
        Review review = Review.builder()
                .user(user)
//                .order(orderRepository.findById(requestDto.orderId()))
                .content(requestDto.content())
                .rating(requestDto.rating())
                .build();

        reviewRepository.save(review);

        if ( !requestDto.file().isEmpty() ){
            photoUpload(requestDto.file(), review);
        }

        return ResponseEntity.ok().body(ReviewResponseDto.from(review));
    }

    @Transactional
    public ResponseEntity<ReviewResponseDto> updateReview(UUID id, ReviewRequestDto requestDto, UUID userId) {

        if (requestDto.content().isEmpty()) throw new IllegalArgumentException("본문이 비어있습니다. 리뷰 내용을 입력해주세요.");

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 리뷰를 찾을 수 없습니다."));

        if ( !review.getUser().getId().equals(userId) ) throw new IllegalArgumentException("수정 권한이 없습니다.");

        review.update(requestDto);
        reviewRepository.save(review);

        if ( !requestDto.file().isEmpty() ){
            photoUpload(requestDto.file(), review);
        }

        return ResponseEntity.ok().body(ReviewResponseDto.from(review));
    }

    public ResponseEntity<ReviewResponseDto> deleteReview(UUID id, UUID userId) {

        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 리뷰를 찾을 수 없습니다."));

        if( !review.getUser().getId().equals(userId) ) throw new IllegalArgumentException("삭제 권한이 없습니다.");

        review.delete(id);
        reviewRepository.save(review);
        return ResponseEntity.ok().body(ReviewResponseDto.from(review));
    }

    private void photoUpload(List<MultipartFile> photos, Review review){
        List<String> photoUrls;

        try {
            photoUrls = fileUpload.uploadMultipleFile(photos);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 오류 발생");
        }

        List<ReviewPhoto> photoList = photoUrls.stream()
                .map(url -> ReviewPhoto.builder()
                        .review(review)
                        .url(url)
                        .build())
                .toList();

        reviewPhotoRepository.saveAll(photoList);
    }
}
