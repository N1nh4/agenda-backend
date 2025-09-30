package com.engenharia_software.agenda.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

    @Value("${frontend.local.url:http://localhost:3000}")
    private String localFrontendUrl;

    @Value("${frontend.prod.url:https://agenda-frontend-xi.vercel.app}")
    private String prodFrontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita CSRF, pois vamos usar JWT
            .csrf(csrf -> csrf.disable())

            // Define acesso público
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/login",
                    "/auth/me",
                    "/auth/logout",
                    "/agenda",
                    "/usuario",
                    "/minha_agenda/**",
                    "/uploads/**",
                    "/h2-console/**"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // Configuração de CORS
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new CorsConfiguration();
                corsConfig.setAllowedOrigins(List.of(localFrontendUrl, prodFrontendUrl));
                corsConfig.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setExposedHeaders(List.of("Authorization")); // JWT será enviado aqui
                corsConfig.setAllowCredentials(false); // JWT no storage, não cookies
                return corsConfig;
            }))

            // Desabilita sessões, JWT não usa sessão
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}
