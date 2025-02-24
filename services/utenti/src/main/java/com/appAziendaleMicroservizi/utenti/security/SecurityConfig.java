package com.appAziendaleMicroservizi.utenti.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        List<String> publicEndpoints = List.of(
                "/app/v1/auth/register",
                "/app/v1/auth/login",
                "/app/v1/auth/confirm",
                "/app/v1/auth/reset_pw",
                "/app/v1/auth/forcePassword"
        );

        return http
                .csrf(AbstractHttpConfigurer::disable) // Disabilita CSRF se non necessario
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicEndpoints.toArray(new String [0]))
                        .permitAll() // Rendi pubblico
                        .anyRequest()
                        .permitAll() // Tutto il resto richiede autenticazione
                )
                .build();
    }
}
