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


    //order
    ORDER_CREATE_SUCCESS("주문 생성 성공"),
    ORDER_LIST_SUCCESS("주문 리스트 조회 성공"),
    ORDER_STATUS_CHANGE_SUCCESS("주문 상태 변경 성공"),
    ORDER_DETAIL_SUCCESS("주문 상세 조회 성공"),
    ORDER_CANCEL_SUCCESS("주문 취소 성공"),
    // 다른 도메인 추가...





    ;
    private final String message;
}
