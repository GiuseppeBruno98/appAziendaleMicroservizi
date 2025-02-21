package com.appAziendaleMicroservizi.api_gateway.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;



import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // Definisci i tuoi endpoint pubblici
        List<String> publicEndpoints = List.of(
                "/app/v1/auth/register",
                "/app/v1/auth/login",
                "/app/v1/auth/confirm",
                "/app/v1/auth/reset_pw",
                "/app/v1/auth/forcePassword",
                "/app/v1/utenti/all",
                "/app/v1/timbri/all"
        );

        // Imposta i tuoi endpoint pubblici
        jwtAuthFilter.setPublicEndpoints(publicEndpoints);

        // Configura la sicurezza
        System.out.println(publicEndpoints);
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)  // Disabilita CSRF se non è necessario
                .authorizeExchange(exchanges -> {
                    // Permetti l'accesso agli endpoint pubblici
                    publicEndpoints.forEach(endpoint -> exchanges.pathMatchers(endpoint).permitAll());
                    // Tutte le altre richieste richiedono autenticazione
                    exchanges.anyExchange().authenticated();
                })
                .authenticationManager(reactiveAuthenticationManager)  // Usa il tuo authenticationManager
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();  // Aggiungi il filtro JWT alla catena di filtri
    }


}
