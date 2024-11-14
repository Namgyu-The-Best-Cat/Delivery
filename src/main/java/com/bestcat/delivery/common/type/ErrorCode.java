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


    // area
    SERVICE_AREA_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 지역입니다."),


    // delivery
    DELIVERY_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배달 주소를 찾을 수 없습니다."),

    // store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),

    // 다른 도메인 추가...

    ;
    private final HttpStatus httpStatus;
    private final String description;
}

