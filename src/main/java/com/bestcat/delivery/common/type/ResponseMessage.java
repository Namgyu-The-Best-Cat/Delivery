package com.bestcat.delivery.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    // 유저 관련
    SIGN_UP_SUCCESS("회원가입 성공"),
    SIGN_IN_SUCCESS("로그인 성공"),
    LOGOUT_SUCCESS("로그아웃 성공"),
    GET_USER_INFO_SUCCESS("사용자 정보 조회 성공"),
    DELETE_USER_SUCCESS("유저 삭제 성공"),
    NICKNAME_CANT_USE("닉네임 사용 불가"),
    NICKNAME_CAN_USE("닉네임 사용 가능"),
    USERNAME_CAN_USE("사용자 아이디 사용 가능"),
    USERNAME_CANT_USE("사용자 아이디 사용 불가"),
    PASSWORD_UPDATE_SUCCESS("비밀번호 변경 성공"),
    NICKNAME_UPDATE_SUCCESS("닉네임 변경 성공"),


    // 다른 도메인 추가...





    ;
    private final String message;
}
