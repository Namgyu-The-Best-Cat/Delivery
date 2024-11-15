package com.bestcat.delivery.review.web;

import com.bestcat.delivery.common.util.UserDetailsImpl;
import com.bestcat.delivery.review.dto.ReviewResponseDto;
import com.bestcat.delivery.review.dto.ReviewRequestDto;
import com.bestcat.delivery.review.service.ReviewPhotoService;
import com.bestcat.delivery.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;
    private final ReviewPhotoService reviewPhotoService;

    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getStoreReview(@PathVariable("storeId") String storeId,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("limit") int limit,
                                                                 @RequestParam("sortBy") String sortBy,
                                                                 @RequestParam("isAsc") boolean isAsc) {
        return reviewService.getStoreReview(UUID.fromString(storeId), page, limit, sortBy, isAsc);
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getUserReview(@PathVariable("userId") String userId,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("limit") int limit,
                                                 @RequestParam("sortBy") String sortBy,
                                                 @RequestParam("isAsc") boolean isAsc) {
        return reviewService.getUserReview(UUID.fromString(userId), page, limit, sortBy, isAsc);
    }

    @PostMapping(value = "/reviews", consumes = "multipart/form-data")
    public ResponseEntity<ReviewResponseDto> createReview(@ModelAttribute ReviewRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){

        return reviewService.createReview(requestDto, userDetails.getUser());
    }

    @PutMapping(value = "/reviews/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable UUID id, @ModelAttribute ReviewRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){

        return reviewService.updateReview(id, requestDto, userDetails.getUserId());
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> deleteReview(@PathVariable UUID id
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return reviewService.deleteReview(id, userDetails.getUserId());
    }

}
