package com.bestcat.delivery.user.dto;

import com.bestcat.delivery.common.util.UserDetailsImpl;

public record UserInfoDto (
        String username,
        String email,
        String nickname
){
    public UserInfoDto(UserDetailsImpl userDetails) {
        this(userDetails.getUser().getUsername(),
                userDetails.getUser().getEmail(),
                userDetails.getUser().getNickname());
    }
}