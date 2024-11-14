package com.bestcat.delivery.common.util;

import static com.bestcat.delivery.common.type.ErrorCode.CANNOT_FOUND_LOGIN_USER;
import static com.bestcat.delivery.common.type.ResponseMessage.LOGOUT_SUCCESS;

import com.bestcat.delivery.common.dto.ErrorResponse;
import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.common.type.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private static final Logger logger = LoggerFactory.getLogger("로그아웃 관련 로그");
    private final JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws AuthorizationDeniedException{
        String tokenValue = jwtUtil.getTokenFromRequest(request);

        if (StringUtils.hasText(tokenValue)) {
            // JWT 토큰 substring
            tokenValue = jwtUtil.substringToken(tokenValue);

            if (!jwtUtil.validateToken(tokenValue)) {
                logger.error("Token Error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            // 쿠키 이름에 따라 삭제할 쿠키 설정
            logger.info("로그아웃 시도 : {}", info.get("username"));

            Cookie cookie = new Cookie("Authorization", null);
            cookie.setMaxAge(0);  // 쿠키의 만료시간을 0으로 설정하여 즉시 삭제
            cookie.setPath("/");  // 쿠키 경로를 설정 (기본 경로로 설정)
            response.addCookie(cookie);
            logger.info("로그아웃 성공");
        } else {
            logger.error("로그인 되어있지 않습니다.");

            try {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .code(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED))
                        .message(CANNOT_FOUND_LOGIN_USER.getDescription())
                        .build();

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                response.getWriter().flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void configureLogout(LogoutConfigurer<HttpSecurity> logout) throws AuthorizationDeniedException {
        // 로그인 성공 메시지 작성
        SuccessResponse<ResponseMessage> successResponse = SuccessResponse.of(LOGOUT_SUCCESS);
        logout
                .logoutUrl("/api/users/logout")  // 로그아웃 URL 설정
                .addLogoutHandler(this)  // 커스텀 로그아웃 핸들러 추가
                .logoutSuccessHandler((request, response, authentication) -> {
                    if (!response.isCommitted()) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(new ObjectMapper().writeValueAsString(successResponse));
                        response.getWriter().flush();
                    }
                })
                .permitAll();
    }

}
