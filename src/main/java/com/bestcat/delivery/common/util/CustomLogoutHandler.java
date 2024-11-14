package com.bestcat.delivery.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    private static final Logger logger = LoggerFactory.getLogger("로그아웃 관련 로그");

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 쿠키 이름에 따라 삭제할 쿠키 설정
        logger.info("로그아웃 시도");

        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);  // 쿠키의 만료시간을 0으로 설정하여 즉시 삭제
        cookie.setPath("/");  // 쿠키 경로를 설정 (기본 경로로 설정)
        response.addCookie(cookie);
        logger.info("로그아웃 성공");
    }

}
