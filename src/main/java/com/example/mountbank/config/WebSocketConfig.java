package com.example.mountbank.config;

import com.example.mountbank.service.NotificationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final NotificationService notificationService;
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    public WebSocketConfig(NotificationService notificationService, JwtHandshakeInterceptor jwtHandshakeInterceptor) {
        this.notificationService = notificationService;
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册 WebSocket 处理器，设置 WebSocket 路径和允许的跨域来源
        registry.addHandler(notificationService, "/notifications")
                .addInterceptors(jwtHandshakeInterceptor)  // 添加 JWT 拦截器
                .setAllowedOrigins("http://localhost:3000");  // 允许来自前端的请求

    }
}
