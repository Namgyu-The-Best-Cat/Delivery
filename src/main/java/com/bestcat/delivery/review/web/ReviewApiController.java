package com.bestcat.delivery.review.web;

import com.bestcat.delivery.review.dto.ReviewResponseDto;
import com.bestcat.delivery.review.dto.ReviewRequestDto;
import com.bestcat.delivery.review.service.ReviewService;
import com.bestcat.delivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @GetMapping("/store/{storeId}/review")
    public Page<ReviewResponseDto> getStoreReview(@PathVariable("storeId") String storeId,
                                                  @RequestParam("page") int page,
                                                  @RequestParam("limit") int limit,
                                                  @RequestParam("sortBy") String sortBy,
                                                  @RequestParam("isAsc") boolean isAsc) {
        return reviewService.getStoreReview(storeId, page, limit, sortBy, isAsc);
    }

    @GetMapping("/user/{userId}/review")
    public Page<ReviewResponseDto> getUserReview(@PathVariable("userId") String userId,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("limit") int limit,
                                                 @RequestParam("sortBy") String sortBy,
                                                 @RequestParam("isAsc") boolean isAsc) {
        return reviewService.getUserReview(userId, page, limit, sortBy, isAsc);
    }

    @PostMapping(value = "/review", consumes = "multipart/form-data")
    public ReviewResponseDto createReview(@ModelAttribute ReviewRequestDto requestDto
            /*, @AuthenticationPrincipal UserDetailsImpl userDetails*/){

        // TODO 파일 업로드 서비스 로직

        return reviewService.createReview(requestDto, userDetails.getUser());
    }

    @PutMapping(value = "/review/{id}", consumes = "multipart/form-data")
    public ReviewResponseDto updateReview(@PathVariable UUID id, @ModelAttribute ReviewRequestDto requestDto
            /*, @AuthenticationPrincipal UserDetailsImpl userDetails*/){

        // TODO 파일 업로드 서비스 로직

        return reviewService.updateReview(id, requestDto, /*userDetails.getUser()*/ new User().getId());
    }

    @DeleteMapping("/review/{id}")
    public ReviewResponseDto deleteReview(@PathVariable UUID id
            /*, @AuthenticationPrincipal UserDetailsImpl userDetails*/) {

        return reviewService.deleteReview(id, /*userDetails.getUser()*/ new User().getId());
    }

}
