package com.bestcat.delivery.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    // 유저 관련
    SIGNUP_SUCCESS("회원가입 성공"),
    SIGNIN_SUCCESS("로그인 성공"),
    GET_USER_INFO_SUCCESS("사용자 정보 조회 성공"),


    // 다른 도메인 추가...





    ;
    private final String message;
}
