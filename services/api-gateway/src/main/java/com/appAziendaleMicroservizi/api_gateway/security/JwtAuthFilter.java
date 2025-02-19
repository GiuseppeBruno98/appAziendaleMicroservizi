package com.appAziendaleMicroservizi.api_gateway.security;

import com.appAziendaleMicroservizi.api_gateway.domains.dto.responses.ErrorResponse;
import com.appAziendaleMicroservizi.api_gateway.services.TokenBlackListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.util.List;


@Component
@Slf4j
public class JwtAuthFilter implements WebFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Setter
    private List<String> publicEndpoints;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestURI = exchange.getRequest().getURI().getPath();
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Se l'endpoint è pubblico, bypassiamo il filtro
        if (isPublicUrl(requestURI)) {
            return chain.filter(exchange);
        }

        // Se non c'è il token, restituiamo un errore
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new RuntimeException("Token JWT mancante o malformato"));
        }

        // Estraggo il token JWT
        String jwt = authHeader.substring(7);

        // Verifico se il token è in blacklist
        if (tokenBlackListService.isPresentToken(jwt)) {
            return Mono.error(new RuntimeException("Token nella blacklist, non è più valido!"));
        }

        // Estraggo l'email dal token
        Mono<String> emailMono = jwtService.extractUsername(jwt);

        return emailMono.flatMap(email -> {
                    if (email == null) {
                        // Restituisco un errore tramite Mono.error()
                        return Mono.error(new RuntimeException("Username non trovato nel token"));
                    }

                    return Mono.fromCallable(() -> userDetailsService.loadUserByUsername(email))
                            .flatMap(userDetails -> {
                                // Creiamo il contesto di autenticazione
                                Authentication authentication = new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities()
                                );

                                // Controllo ruolo TOCONFIRM
                                if (userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .anyMatch(role -> role.equals("TOCONFIRM"))) {
                                    // Restituiamo errore se l'utente non è confermato
                                    return Mono.error(new RuntimeException("Devi confermare l'account!"));
                                }

                                // Salviamo l'autenticazione nel Security Context reattivo
                                return chain.filter(exchange)
                                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                            });
                })
                .onErrorResume(e -> {
                    // Restituiamo un errore tramite Mono.error() in caso di eccezione
                    return Mono.error(new RuntimeException("Errore nell'autenticazione: " + e.getMessage()));
                });
    }



    private Mono<Void> sendAuthErrorResponse(ServerWebExchange exchange, String error, String message) throws JsonProcessingException {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .exception(error)
                .message(message)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                .bufferFactory()
                .wrap(objectMapper.writeValueAsBytes(errorResponse))));
    }

    private boolean isPublicUrl(String requestURI) {
        return publicEndpoints.stream()
                .map(endpoint -> endpoint.replace("**", ".*"))
                .anyMatch(requestURI::matches);
    }
}
