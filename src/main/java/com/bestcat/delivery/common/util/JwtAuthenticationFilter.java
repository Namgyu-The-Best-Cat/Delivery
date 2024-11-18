package com.bestcat.delivery.common.util;

import static com.bestcat.delivery.common.type.ErrorCode.SIGN_IN_FAILED;
import static com.bestcat.delivery.common.type.ResponseMessage.SIGN_IN_SUCCESS;

import com.bestcat.delivery.common.dto.ErrorResponse;
import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.type.ResponseMessage;
import com.bestcat.delivery.user.dto.SigninRequestDto;
import com.bestcat.delivery.user.entity.RoleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            SigninRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), SigninRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.username(),
                            requestDto.password(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        UUID id = ((UserDetailsImpl) authResult.getPrincipal()).getUserId();
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        RoleType role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(id, username, role);
        jwtUtil.addJwtToCookie(token, response);
        // 로그인 성공 메시지 작성
        SuccessResponse<ResponseMessage> successResponse = SuccessResponse.of(SIGN_IN_SUCCESS);

        // JSON 응답을 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(successResponse));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);

        // 로그인 실패 메시지 작성
        ErrorResponse errorResponse = ErrorResponse.builder().code("UnAuthorized").message(SIGN_IN_FAILED.getDescription()).build();
        // JSON 응답을 반환
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        response.getWriter().flush();
    }
}