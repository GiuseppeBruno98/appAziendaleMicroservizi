package com.appAziendaleMicroservizi.api_gateway.configurations;

import com.appAziendaleMicroservizi.api_gateway.services.UtenteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class ApplicationConfig {

    @Autowired
    private UtenteClient utenteClient;

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return username -> Mono.fromCallable(() -> {
            // CHIAMATA SINCRONA A FEIGN (BLOCCANTE)
            // La incapsuliamo in un Callable così da poterla eseguire su un thread dedicato.
            return utenteClient.getUtenteResponseByEmail(username);
            })
            // Sposta l’esecuzione su un thread pool adatto a operazioni bloccanti
            .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())


            // Una volta ottenuto utenteResponse, costruiamo l'UserDetails o lanciamo errore
            .flatMap(utenteResponse -> {
                if (utenteResponse == null) {
                    return Mono.error(new UsernameNotFoundException(
                            "Utente con email " + username + " non trovato"
                    ));
                }
                GrantedAuthority authority = new SimpleGrantedAuthority(utenteResponse.ruolo());
                UserDetails userDetails = User
                                            .withUsername(utenteResponse.email())
                                            .password(utenteResponse.password())
                                            .authorities(List.of(authority))
                                            .build();
                return Mono.just(userDetails);
            });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService reactiveUserDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        authManager.setPasswordEncoder(passwordEncoder);
        return authManager;
    }
}
