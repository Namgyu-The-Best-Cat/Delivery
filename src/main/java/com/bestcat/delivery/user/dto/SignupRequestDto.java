package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.user.entity.RoleType;
import com.bestcat.delivery.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequestDto (
        @NotBlank(message = "유저 아이디을 입력해주세요.")
        @Size(min =8, max = 15, message = "8자 이상 15자 이하로 입력할 수 있습니다.")
        String username,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "최소 8자 이상, 15자 이하의 글자만 입력할 수 있습니다.")
        @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]*$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자(!@#$%^&*) 만 입력 가능합니다.")
        String password,

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 10, message = "최소 2자 이상, 10자 이하의 글자만 입력할 수 없습니다.")
        String nickname,

        @NotBlank(message = "권한을 선택해주세요.")
        String role
) {
    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(RoleType.valueOf(role))
                .build();
    }
}

