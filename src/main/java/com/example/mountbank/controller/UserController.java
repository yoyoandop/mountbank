package com.example.mountbank.controller;

import com.example.mountbank.model.User;
import com.example.mountbank.service.CacheService;
import com.example.mountbank.service.UserService;
import com.example.mountbank.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(
        origins = "http://localhost:3000",
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CacheService cacheService;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil, CacheService cacheService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.cacheService = cacheService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid User user) {
        Map<String, Object> response = userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword(),
                user.getCoverImage(), user.getBiography(), user.getPhoneNumber());

        if ((boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginDetails, HttpServletResponse response) {
        User user = userService.validateLogin(loginDetails.getPhoneNumber(), loginDetails.getPassword());
        String jwtToken = jwtUtil.generateToken(user.getPhoneNumber());

        Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 10);
        // 手动设置 SameSite 属性
        response.setHeader("Set-Cookie", "jwtToken=" + jwtToken + "; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=" + (60 * 60 * 10));
        response.addCookie(jwtCookie);
        return ResponseEntity.ok("Login successful. JWT set in cookie.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    String jwtToken = cookie.getValue();

                    // 獲取 JWT 的過期時間，並將其添加到 Redis 黑名單
                    long expiration = jwtUtil.getTokenExpirationTimeInMillis(jwtToken) - System.currentTimeMillis();
                    cacheService.addToBlacklist(jwtToken, expiration / 1000);  // 以秒為單位將 token 加入黑名單

                    // 使 JWT cookie 失效
                    cookie.setValue(null);
                    cookie.setMaxAge(0);  // 設置 cookie 立即過期
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    response.addCookie(cookie);  // 將更新後的 cookie 添加到響應中

                    break;
                }
            }
        }

        return ResponseEntity.ok("Logout successful. JWT cookie removed and token blacklisted.");
    }
}
