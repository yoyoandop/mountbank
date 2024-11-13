package com.example.mountbank.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

@Component
public class JwtUtil {

    // 使用 SecretKey 來代替手動設定的字符串密鑰
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 使用自動生成的密鑰

    // 从 JWT 中提取 phoneNumber
    public String getPhoneNumberFromToken(String token) {
        return getClaimsFromToken(token).getSubject(); // 获取 JWT 的主体 (subject)
    }

    // 验证 token 是否过期
    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    // 获取 token 的过期时间
    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public long getTokenExpirationTimeInMillis(String token) {
        return getExpirationDateFromToken(token).getTime();
    }

    // 提取 JWT 中的所有 Claims
    private Claims getClaimsFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
        return parser.parseClaimsJws(token).getBody();
    }

    // 生成 JWT
    public String generateToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber) // 使用 phoneNumber 作为 subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 设置过期时间
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 使用新的密钥生成方式
                .compact();
    }
}
