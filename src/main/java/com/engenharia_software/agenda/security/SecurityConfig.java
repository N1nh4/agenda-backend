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

    @Value("${frontend.prod.url:https://agenda-frontend-xi.vercel.app}")
    private String prodFrontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Em APIs com frontend separado, normalmente desabilitamos CSRF
            .csrf(csrf -> csrf.disable())

            // Definindo quem pode acessar o quê
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
                ).permitAll()  // rotas públicas
                .anyRequest().authenticated() // resto precisa de login
            )

            // Configuração de CORS
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new CorsConfiguration();
                corsConfig.setAllowedOrigins(List.of(prodFrontendUrl));
                corsConfig.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
                corsConfig.setAllowCredentials(true);
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setExposedHeaders(List.of("Set-Cookie"));
                return corsConfig;

            }))

            // Configuração de sessão
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) 
            );

        return http.build();
    }
}

