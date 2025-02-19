package com.appAziendaleMicroservizi.api_gateway.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;


import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // Definisci i tuoi endpoint pubblici
        List<String> publicEndpoints = List.of(
                "/app/v1/auth/register",
                "/app/v1/auth/login",
                "/app/v1/auth/confirm",
                "/app/v1/auth/reset_pw",
                "/app/v1/auth/forcePassword"
        );

        // Imposta i tuoi endpoint pubblici
        jwtAuthFilter.setPublicEndpoints(publicEndpoints);

        // Configura la sicurezza
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)  // Disabilita CSRF se non è necessario
                .authorizeExchange(exchanges -> {
                    // Permetti l'accesso agli endpoint pubblici
                    publicEndpoints.forEach(endpoint -> exchanges.pathMatchers(endpoint).permitAll());
                    // Tutte le altre richieste richiedono autenticazione
                    exchanges.anyExchange().authenticated();
                })
                .authenticationManager(authenticationManager())  // Usa il tuo authenticationManager
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION);  // Aggiungi il filtro JWT alla catena di filtri

        return http.build();
    }


}
