package com.appAziendaleMicroservizi.api_gateway.configurations;

import com.appAziendaleMicroservizi.api_gateway.domains.dto.responses.UtenteResponse;
import com.appAziendaleMicroservizi.api_gateway.services.UtenteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ApplicationConfig {

    @Autowired
    private UtenteClient utenteClient;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UtenteResponse utenteResponse = utenteClient.getUtenteResponseByEmail(username);
            if (utenteResponse == null) {
                throw new UsernameNotFoundException("Utente con email " + username + " non trovato");
            }

            // Mappa il ruolo in un GrantedAuthority
            GrantedAuthority authority = new SimpleGrantedAuthority(utenteResponse.ruolo());

            return User.builder()
                    .username(utenteResponse.email())
                    .password(utenteResponse.password())
                    .authorities(List.of(authority)) // Lista con un solo ruolo
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() { // -> oggetto necessario a estrapolare i dati dell'utente
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }


}
