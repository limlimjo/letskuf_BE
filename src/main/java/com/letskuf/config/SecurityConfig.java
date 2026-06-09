package com.letskuf.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity // spring security 활성화
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http    // csrf 보호 비활성화 (개발 단계에서는 보통 끄고, REST API에서는 끄는 경우 많음)
                .csrf(csrf -> csrf.disable())
                // cors 설정 (React에서 요청 허용)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    // 쿠키(JSESSIONID) 포함 허용 -> 세션 기반에서 필수
                    config.setAllowCredentials(true);
                    // React 서버 주소 허용
                    config.addAllowedOrigin("http://localhost:5173");
                    // 모든 헤더 허용
                    config.addAllowedHeader("*");
                    // GET, POST 등 모든 HTTP 메서드 허용
                    config.addAllowedMethod("*");

                    return config;
                }))

                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .successHandler((req, res, auth) -> {
                            res.setStatus(200);
                        })
                        .failureHandler((req, res, ex) -> {
                            res.setStatus(401);
                        })
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(401);
                        })
                        .accessDeniedHandler((req, res, e) -> {
                            res.setStatus(403);
                        })
                )

                // URL별 권한 설정 (인가)
                .authorizeHttpRequests(auth -> auth
                        // 로그인은 누구나 가능
                        .requestMatchers("/api/login", "/api/logout").permitAll()
                        // /admin/**는 ADMIN 권한 필요
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/me").authenticated()
                        // 나머지는 로그인만 하면 접근 가능
                        .anyRequest().authenticated()
                )
                // 세션 정책
                .sessionManagement(session -> session
                        // 필요할 때만 세션 생성
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        // /logout 요청 시 로그아웃 처리
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((req, res, auth) -> {
                            res.setStatus(200);
                        })
                        // 세션 완전히 삭제
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        // 내부적으로 UserDetailsService + PasswordEncoder 사용해서 인증 처리
        return config.getAuthenticationManager();
    }
}
