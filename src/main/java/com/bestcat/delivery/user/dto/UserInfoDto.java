package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.user.entity.User;

public record UserInfoDto (
        String username,
        String email,
        String nickname
){
    public UserInfoDto(User user) {
        this(user.getUsername(),
                user.getEmail(),
                user.getNickname());
    }
}