package com.okbo_projects.common.utils;

import io.jsonwebtoken.*;
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
}