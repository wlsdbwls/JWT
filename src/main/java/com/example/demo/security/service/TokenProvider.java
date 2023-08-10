package com.example.demo.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@PropertySource("classpath:jwt.properties")
@Service
public class TokenProvider {
    private final String secretKey;
    private final long expirationHours;
    private final String issuer;
//    private long refreshTokenValidTime = 1000L * 60 * 60 * 24; // 1일

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-hours}") long expirationHours,
            @Value("${issuer}") String issuer
    ) {
        this.secretKey = secretKey;
        this.expirationHours = expirationHours;
        this.issuer = issuer;
    }

    // AccessToken 생성
    public String createToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject(userSpecification)  // JWT 토큰 제목
                .setIssuer(issuer)  // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    // RefreshToken 생성
//    public String createRefreshToken() {
//
//        return Jwts.builder()
//                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
//                .setExpiration(Date.from(Instant.now().plus(refreshTokenValidTime, ChronoUnit.HOURS)))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }

    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
