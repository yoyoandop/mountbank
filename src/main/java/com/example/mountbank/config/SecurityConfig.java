package com.example.mountbank.config;

import com.example.mountbank.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 添加密码加密支持
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()); // 配置密码加密器
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and() // 启用 CORS 配置
                .csrf().disable() // 禁用 CSRF（適合前後端分離應用，否則需要額外設置 CSRF token）
                .authorizeRequests()
                // 配置 WebSocket 路徑不需要安全過濾
                .antMatchers("/notifications").authenticated() // 受保護端點 // 允許 WebSocket 握手請求，不需要 JWT 認證
                .antMatchers("/api/posts").authenticated()  // 受保護的 POST 和 GET 請求
                .antMatchers("/api/posts/{postId}/like").authenticated()  // 受保護的 like 操作
                .antMatchers("/api/images/**", "/api/images/book/**").permitAll()  // 開放這些路徑
                .antMatchers("/api/users/login", "/api/users/register").permitAll()
                .antMatchers("/info").permitAll()
                .antMatchers("/posts", "/posts/{id}").authenticated()
                .antMatchers("/comments/add/{postId}").authenticated()
                .anyRequest().authenticated()


                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用 BCrypt 进行密码加密
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 允许的前端来源
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 允许的方法
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 允许的头部
        configuration.setAllowCredentials(true); // 允许携带凭证
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 這是實現 CorsConfigurationSource 接口的類
        source.registerCorsConfiguration("/**", configuration); // 對所有路徑適用此配置
        return source; // 返回正確的 CorsConfigurationSource 實例
    }
}
