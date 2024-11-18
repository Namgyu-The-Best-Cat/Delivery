package com.bestcat.delivery.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordRequestDto (
        @NotBlank(message = "기존 비밀번호를 입력해주세요.")
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]*$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자(!@#$%^&*) 만 입력 가능합니다.")
        String password,

        @NotBlank(message = "바꾸실 비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "최소 8자 이상, 15자 이하의 글자만 입력할 수 있습니다.")
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]*$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자(!@#$%^&*) 만 입력 가능합니다.")
        String newPassword
) {
}
