package com.example.mountbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public CacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 將 JWT 添加到 Redis 作為黑名單
    public void addToBlacklist(String token, long expirationTimeInSeconds) {
        redisTemplate.opsForValue().set(token, "blacklisted", expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    // 檢查 JWT 是否在黑名單中
    public boolean isTokenBlacklisted(String token) {
        Boolean isBlacklisted = redisTemplate.hasKey(token);
        return isBlacklisted != null && isBlacklisted;  // 確保不為 null 且為 true
    }

    // 清除特定的 Token（例如登出或其他管理操作）
    public void removeToken(String token) {
        redisTemplate.delete(token);
    }
}
