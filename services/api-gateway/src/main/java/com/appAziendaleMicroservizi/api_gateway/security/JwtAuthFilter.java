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
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ResponseStatusException;
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
    private ReactiveUserDetailsService reactiveUserDetailsService;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Setter
    private List<String> publicEndpoints;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestURI = exchange.getRequest().getURI().getPath();


        // Se l'endpoint è pubblico, bypassiamo il filtro
        if (isPublicUrl(requestURI)) {
            return chain.filter(exchange);
        }
        // Se non c'è il token, restituiamo un 401
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.sendAuthErrorResponse(exchange, "MissingToken", "Token JWT mancante o malformato");
        }

        // Estraggo il token JWT
        String jwt = authHeader.substring(7);

        // Verifico la blacklist
        if (tokenBlackListService.isPresentToken(jwt)) {
            return this.sendAuthErrorResponse(exchange, "Blacklisted", "Token nella blacklist, non è più valido!");
        }

        // Estraggo l'email dal token
        Mono<String> emailMono = jwtService.extractUsername(jwt);

        return emailMono
                .flatMap(email -> {
                    if (email == null) {
                        return this.sendAuthErrorResponse(exchange, "InvalidToken", "Username non trovato nel token");
                    }

                    // Carichiamo l'utente in modo reattivo
                    return reactiveUserDetailsService.findByUsername(email)
                            .flatMap(userDetails -> validateUserAndContinue(exchange, chain, jwt, userDetails))
                            .onErrorResume(ex -> {
                                // Se la findByUsername fallisce (UsernameNotFoundException), restituire un 401
                                return this.sendAuthErrorResponse(exchange, "UserNotFound", ex.getMessage());
                            });
                })
                .onErrorResume(e -> {
                    // Altri errori generici (es. token scaduto, problemi di parsing, ecc.)
                    return this.sendAuthErrorResponse(exchange, "AuthError", e.getMessage());
                });
    }

    /**
     * Se l'utente esiste, verifichiamo le scadenze, lo stato "TOCONFIRM", ecc.
     */
    private Mono<Void> validateUserAndContinue(ServerWebExchange exchange,
                                               WebFilterChain chain,
                                               String jwt,
                                               UserDetails userDetails) {
        // Controllo scadenza token
        System.out.println("pippo");
        return jwtService.isTokenValid(jwt, userDetails.getUsername())
                .flatMap(isValid -> {
                    if (!isValid) {
                        return this.sendAuthErrorResponse(exchange, "InvalidToken", "Token non valido o scaduto");
                    }

                    // Controllo ruolo TOCONFIRM
                    boolean toConfirm = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .anyMatch(role -> role.equals("TOCONFIRM"));

                    if (toConfirm) {
                        return this.sendAuthErrorResponse(exchange, "AccountNotConfirmed",
                                "Devi confermare l'account!");
                    }

                    // Creiamo il contesto di autenticazione
                    Authentication authentication =
                            new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    // Iniettiamo l'autenticazione nel Security Context reattivo e proseguiamo
                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                });
    }

    /**
     * Invia al client un errore di tipo 401 (o 403 se preferisci)
     * con un JSON custom e termina la catena reattiva.
     */
    private Mono<Void> sendAuthErrorResponse(ServerWebExchange exchange, String error, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .exception(error)
                .message(message)
                .build();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            return exchange.getResponse().writeWith(Mono.just(
                    exchange.getResponse().bufferFactory().wrap(bytes)
            ));
        } catch (JsonProcessingException e) {
            // In caso di fallo nella serializzazione, lanciamo un errore più generico
            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno"));
        }
    }

    private boolean isPublicUrl(String requestURI) {
        return publicEndpoints.stream()
                .map(endpoint -> endpoint.replace("**", ".*"))
                .anyMatch(requestURI::matches);
    }
}
