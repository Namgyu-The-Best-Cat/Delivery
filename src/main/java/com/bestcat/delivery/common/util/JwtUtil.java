package com.bestcat.delivery.common.util;


import static com.bestcat.delivery.common.type.ResponseMessage.LOGOUT_SUCCESS;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.common.exception.RestApiException;
import com.bestcat.delivery.common.type.ResponseMessage;
import com.bestcat.delivery.user.entity.RoleType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

// @Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {
    // Header
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String Bearer_PREFIX = "Bearer ";
    // 토큰 만료 시간
    private final long TOKEN_TIME = 60 * 60 * 1000;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    // 알고리즘 선택
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    private static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    // 여러번 요정되는 것을 방지
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 생성
    public String createToken(UUID userId, String username, RoleType role) {
        Date date = new Date();

        return Bearer_PREFIX +
                Jwts.builder()
                        .setSubject(userId.toString()
                        )   // 사용자 식별 ID
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .claim("username", username)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))   // 만료 시간
                        .setIssuedAt(date)  // 발급일
                        .signWith(key, signatureAlgorithm)  // 암호화 알고리즘
                        .compact();
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replace("\\+", "%20");

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);    // name-value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);

        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    // JWT substring
    public String substringToken(String token) {
        if(StringUtils.hasText(token) && token.startsWith(Bearer_PREFIX)) {
            return token.substring(Bearer_PREFIX.length());
        }
        logger.error("Not Found Token");

        throw new NullPointerException("Not Found Token");
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException | SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT, 만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT, 지원하지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 입니다.");
        }

        return false;
    }

    // token 에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsername(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public List<String> getRoles(String token) {

        List<?> roles = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("roles", List.class);

        if (roles != null) {
            return roles.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    // HttpServletRequest 에서 Cookie Value : JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }


}
