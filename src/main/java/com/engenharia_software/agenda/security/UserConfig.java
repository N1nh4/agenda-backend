package com.engenharia_software.agenda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // cria usuário com ROLE_USER
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("1234")) // senha criptografada
            .roles("USER")
            .build();

        // cria usuário com ROLE_ADMIN
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("1234")) // senha criptografada
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // recomendado
    }
}
