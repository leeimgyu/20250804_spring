package com.example.ex8.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

// 토큰이란?
// 서버가 사용자를 인증하면 토큰을 발급.
// 이후 클라이언트는 요청마다 이 토큰을 헤더에 붙여 보냄.
// 서버는 토큰만 검증하면 되므로 서버에 로그인 상태를 저장할 필요 없음.

// 토큰의 특징
// 임시 열쇠(Key) 역할 → 비밀번호 대신 안전하게 사용.
// 일반적으로 만료 시간(expiration) 이 있음.
// 보통은 HTTP 헤더에 포함해서 전달:

//토큰 인증 흐름
// 1. 사용자가 로그인 요청 (아이디/비번 전송).
// 2. 서버가 사용자 확인 후 Access Token(＋Refresh Token) 발급.
// 3. 클라이언트는 API 요청 시 헤더에 Access Token 첨부.
// 4. 서버는 토큰 유효성(서명, 만료시간 등) 검증 → 성공하면 응답.
// 5. Access Token 만료 시 Refresh Token으로 재발급.

@Log4j2
public class JWTUtil {
  private String secretKey = "1234567890abcdefghijklmnopqrstuvwxyz";
  private long expire = 60 * 24 * 30;

  public String generateToken(String content) throws Exception {
    return Jwts.builder()
        .issuedAt(new Date())
        .expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
        //.expiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant()))
        .claim("sub", content)
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
        .compact();
  }

  public String validateAndExtract(String tokenStr) throws Exception {
    Jwt jwt = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
        .build().parse(tokenStr);
    log.info("Jwts getClass; " + jwt);
    Claims claims = (Claims) jwt.getPayload();
    return (String) claims.get("sub");
  }
}
