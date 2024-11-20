package com.example.mountbank.filter;

import com.example.mountbank.service.CacheService;
import com.example.mountbank.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CacheService cacheService;

    // Constructor injection
    public JwtRequestFilter(JwtUtil jwtUtil, CacheService cacheService) {
        this.jwtUtil = jwtUtil;
        this.cacheService = cacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String jwt = null;
        String phoneNumber = null;

        // 如果请求是登录或注册接口，跳过 JWT 验证
        String uri = request.getRequestURI();
        if (uri.equals("/api/users/login") || uri.equals("/api/users/register") || uri.equals("/api/images/{id}") || uri.equals("/api/images/book/{bookName}") ) {
            chain.doFilter(request, response);  // 继续处理请求
            return;  // 直接返回，避免进入 JWT 验证逻辑
        }

        // 提取 JWT 从 HttpOnly cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        // 如果 JWT 存在，进行验证
        if (jwt != null) {
            // 检查是否被列入黑名单
            if (cacheService.isTokenBlacklisted(jwt)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;  // 如果 JWT 被黑名单，返回未授权
            }

            // 从 JWT 提取 phoneNumber
            phoneNumber = jwtUtil.getPhoneNumberFromToken(jwt);

        }

        // 如果有效的 JWT 和 phoneNumber 被提取出来，设置认证上下文
        if (phoneNumber != null && !jwtUtil.isTokenExpired(jwt)) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(phoneNumber, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 继续过滤链
        chain.doFilter(request, response);
    }
}
