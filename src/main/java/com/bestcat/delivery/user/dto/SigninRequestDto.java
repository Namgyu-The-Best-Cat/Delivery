package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.user.entity.User;

public record SigninRequestDto(
        String username,
        String password
) {
    public User toEntity() {
        return User.builder().username(username).password(password).build();
    }
}
