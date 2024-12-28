package com.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(
                    "/api-docs",
                    "/api-docs/**",
                    "/v3/api-docs/**",
                    "/v3/api-docs",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/api/login/**",
                    "/api/items/**",
                    "/api/utenti/registra",
                    "/api/utenti/registra/gestore",
                    "/api/utenti/login",
                    "/api/aste/**"
                ).permitAll();
                auth.anyRequest().authenticated();
            })
            .build();
    }
}