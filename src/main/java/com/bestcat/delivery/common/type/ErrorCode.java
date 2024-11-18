package com.bestcat.delivery.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    NOT_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "인증된 사용자가 아닙니다."),
    UNEXPECTED_PRINCIPAL_TYPE(HttpStatus.BAD_REQUEST, "예상치 못한 Principal 타입입니다."),


    // 유저
    USER_ID_MISMATCH(HttpStatus.BAD_REQUEST, "로그인한 유저와 요청된 user-id가 일치하지 않습니다."),
    ALREADY_EXIST_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_ROLE(HttpStatus.NOT_FOUND, "유효한 권한이 아닙니다."),
    SIGN_IN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다"),
    NEW_PASSWORD_SAME_AS_CURRENT(HttpStatus.BAD_REQUEST, "현재 사용중인 비밀번호와 일치합니다."),
    CANNOT_FOUND_LOGIN_USER(HttpStatus.UNAUTHORIZED, "현재 로그인 된 유저가 없습니다."),


    // area
    AREA_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 지역입니다."),


    // delivery
    DELIVERY_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배달 주소를 찾을 수 없습니다."),

    // store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),

    // order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 주문 상태입니다."),

    // menu
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),

    // review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),
    CONTENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "본문이 비어있습니다. 리뷰 내용을 입력해주세요."),

    // ai
    AI_RESPONSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Ai 설명 생성에 오류가 발생했습니다."),

    // category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다.")

    // payment
    INVALID_PAYMENT_METHOD(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 수단입니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 결제를 찾을 수 없습니다."),
    // 다른 도메인 추가...

    ;
    private final HttpStatus httpStatus;
    private final String description;
}

