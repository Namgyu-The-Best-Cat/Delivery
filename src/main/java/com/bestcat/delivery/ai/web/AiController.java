package com.bestcat.delivery.ai.web;

import com.bestcat.delivery.ai.dto.AiRequestDto;
import com.bestcat.delivery.ai.dto.AiResponseDto;
import com.bestcat.delivery.ai.service.AiService;
import com.bestcat.delivery.common.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AiController {

    private final AiService aiService;

    @GetMapping("/ai-description")
    public ResponseEntity<AiResponseDto> createDescription(@RequestBody AiRequestDto requestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return aiService.createDescription(requestDto, userDetails.getUser());
    }
}
