package com.bestcat.delivery.review.service;

import com.bestcat.delivery.review.dto.ReviewRequestDto;
import com.bestcat.delivery.review.repository.ReviewPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewPhotoService {

    private final ReviewPhotoRepository reviewPhotoRepository;

    public void updateReviewPhoto(UUID id, ReviewRequestDto requestDto, UUID userId) {

    }
}
