package com.example.mountbank.config;

import com.example.mountbank.service.CacheService;
import com.example.mountbank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.http.HttpStatus;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;
    private final CacheService cacheService;

    @Autowired
    public JwtHandshakeInterceptor(JwtUtil jwtUtil, CacheService cacheService) {
        this.jwtUtil = jwtUtil;
        this.cacheService = cacheService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String jwtToken = null;
        String phoneNumber = null;

        // 使用 ServletServerHttpRequest 来获取 cookies
        if (request instanceof org.springframework.http.server.ServletServerHttpRequest) {
            HttpServletRequest httpRequest = ((org.springframework.http.server.ServletServerHttpRequest) request).getServletRequest();
            // 获取 HttpOnly cookie 中的 jwtToken
            if (httpRequest.getCookies() != null) {
                for (Cookie cookie : httpRequest.getCookies()) {
                    if ("jwtToken".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        break;
                    }
                }
            }

            // 如果 JWT 存在，则进一步验证
            if (jwtToken != null) {
                // 检查 JWT 是否被列入黑名单
                if (cacheService.isTokenBlacklisted(jwtToken)) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return false;  // 黑名单中的 token，不允许连接
                }

                // 提取 phoneNumber 并验证 JWT 是否过期
                if (!jwtUtil.isTokenExpired(jwtToken)) {
                    phoneNumber = jwtUtil.getPhoneNumberFromToken(jwtToken);
                    // 如果 JWT 有效，允许 WebSocket 连接

                    attributes.put("jwtToken", jwtToken);
                    attributes.put("phoneNumber", phoneNumber);
                    return true;  // 允许连接
                }
            }
        }

        // 如果没有有效的 JWT 或者被列入黑名单，拒绝连接
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 握手后操作（可选）
    }
}
