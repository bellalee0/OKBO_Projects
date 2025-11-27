package com.okbo_projects.common.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    private SecretKey key;
    private JwtParser parser;

    private final long TOKEN_TIME = 24 * 60 * 60 * 1000L;

    @PostConstruct
    public void init() {

        byte[] bytes = Decoders.BASE64.decode(secretKeyString);

        this.key = Keys.hmacShaKeyFor(bytes);
        this.parser = Jwts.parser().verifyWith(this.key).build();
    }

    // 토큰 생성
    public String generateToken(long userId) {

        Date date = new Date();

        return Jwts.builder()
                .claim("userId", userId)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(this.key, Jwts.SIG.HS256)
                .compact();
    }

    // 토큰 안의 유저ID만 가져오기
    public Long getUserId(String jwt) {

        return parser.parseSignedClaims(jwt).getPayload().get("userId", Long.class);
    }

    // 토큰의 유효성 확인
    public boolean validateToken(String jwt) {

        if (jwt == null || jwt.isBlank()) {
            return false;
        }

        try {
            parser.parseSignedClaims(jwt);
            return true;
        } catch (Exception e) {
            log.error("잘못된 JWT 토큰 입니다.", e);
            return false;
        }
    }
}