package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.user.entity.User;
import java.util.UUID;

public record UserInfoDto (
        UUID id,
        String username,
        String email,
        String nickname
){
    public UserInfoDto(User user) {
        this(user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getNickname());
    }
}