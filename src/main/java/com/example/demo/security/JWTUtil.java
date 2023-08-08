//package com.example.demo.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.log4j.Log4j2;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.time.ZonedDateTime;
//import java.util.*;
//
//@Component
//@Slf4j
//public class JWTUtil {
////    @Value("${jwt.secret}")
//    private String key = "1";
////    @Value("${uri.exclude.tokenCheckFilter}")
//    private List<String> tokenCheckFilterExcludeUris = new ArrayList<>();
//    public boolean isTokenCheckFilterExcludeUris(String uri) {
//        return isExcludeUris(uri, tokenCheckFilterExcludeUris);
//    }
//    private boolean isExcludeUris(String uri, List<String> excludeUris) {
//        for (String excludeUri : excludeUris) {
//            if (uri.startsWith(excludeUri)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public String generateToken(Map<String, Object> valueMap, int minutes) {
//        //헤더 부분
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("typ", "JWT");
//        headers.put("alg", "HS256");
//
//        //payload 부분 설정
//        Map<String, Object> payloads = new HashMap<>();
//        payloads.putAll(valueMap);
//
//        String jwtStr = Jwts.builder()
//                .setHeader(headers)
//                .setClaims(payloads)
//                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
//                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(minutes).toInstant()))
//                .signWith(SignatureAlgorithm.HS256, key.getBytes())
//                .compact();
//
//        return jwtStr;
//    }
//    public Map<String, Object> validateToken(String token) throws io.jsonwebtoken.SignatureException, io.jsonwebtoken.ExpiredJwtException {
//        Map<String, Object> claim = Jwts.parser()
//                .setSigningKey(key.getBytes()) // Set Key
//                .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
//                .getBody();
//
//        return claim;
//    }
//}
