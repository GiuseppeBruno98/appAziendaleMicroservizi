package com.appAziendaleMicroservizi.timbri.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                "/app/v1/auth/forcePassword",
                "/app/v1/utenti/all",
                "/app/v1/timbri/all"
        );
        return http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF se non necessario
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicEndpoints.toArray(new String [0])).permitAll() // Rendi pubblico
                        .anyRequest().authenticated() // Tutto il resto richiede autenticazione
                )
                .build();
    }
}
