package com.bestcat.delivery.user.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record NicknameRequestDto(
        @NotBlank
        @Length(min = 2, max = 10, message = "최소 2글자, 최대 10글자 이내로 등록 가능합니다.")
        String nickname
) {
}

