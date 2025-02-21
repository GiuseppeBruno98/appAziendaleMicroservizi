package com.appAziendaleMicroservizi.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key signingKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Mono<String> extractUsername(String token) {
        return Mono.fromSupplier(() -> extractClaim(token, Claims::getSubject));
    }

    public Mono<Date> extractExpirationDate(String token) {
        return Mono.fromSupplier(() -> extractClaim(token, Claims::getExpiration));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Mono<Boolean> isTokenValid(String token, String username) {
        return extractUsername(token)
                .map(extractedUsername -> extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token)
                .map(expirationDate -> expirationDate.before(new Date()))
                .block(); // Uso `.block()` perché non possiamo restituire `Mono<Boolean>` in un metodo sincrono.
    }
}
