package com.appAziendaleMicroservizi.utenti.security;

import com.example.AppAziendale.domains.Entities.Utente;
import com.example.AppAziendale.domains.dto.requests.AuthRequest;
import com.example.AppAziendale.domains.dto.requests.ChangePasswordRequest;
import com.example.AppAziendale.domains.dto.requests.RegisterRequest;
import com.example.AppAziendale.domains.dto.responses.AuthenticationResponse;
import com.example.AppAziendale.domains.dto.responses.ErrorResponse;
import com.example.AppAziendale.domains.dto.responses.GenericResponse;
import com.example.AppAziendale.domains.enums.Role;
import com.example.AppAziendale.services.PosizioneLavorativaService;
import com.example.AppAziendale.services.TokenBlackListService;
import com.example.AppAziendale.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenBlackListService tokenBlackListService;
    @Autowired
    private PosizioneLavorativaService posizioneLavorativaService;
    @Autowired
    private JavaMailSender javaMailSender;

    public AuthenticationResponse register(RegisterRequest request) {
        Utente utente = Utente
                .builder()
                .nome(request.nome())
                .cognome(request.cognome())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .dataNascita(request.dataNascita())
                .luogoNascita(request.luogoNascita())
                .telefono(request.telefono())
                .indirizzo(request.indirizzo())
                .ruolo(Role.TOCONFIRM)
                .imgUtente(request.imgUtente())
                .idPosizioneLavorativa(posizioneLavorativaService.getById(request.idPosizioneLavorativa().id()))
                .build();
        String jwtToken = jwtService.generateToken(utente);
        utente.setRegistrationToken(jwtToken);

        utenteService.insertUtente(utente);
        String confirmationUrl = "http://localhost:8080/app/v1/auth/confirm?token=" + utente.getRegistrationToken();
        javaMailSender.send(createConfirmationEmail(utente.getEmail(), confirmationUrl));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email().toLowerCase(),
                request.password()
        ));
        Utente utente = utenteService.getByEmail(request.email());
        String token = jwtService.generateToken(utente);
        utente.setLastLogin(LocalDateTime.now());
        utenteService.insertUtente(utente);
        return AuthenticationResponse.builder().token(token).build();
    }

    public GenericResponse logout(Long idUtente, String token) {
        tokenBlackListService.insertToken(idUtente,token);
        return GenericResponse.builder().message("Logout effettuato con successo").build();
    }

    private SimpleMailMessage createConfirmationEmail(String email, String confirmationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // a chi mando la mail
        message.setReplyTo("crinq98@gmail.com"); // a chi rispondo se faccio "rispondi"
        message.setFrom("crinq98@gmail.com"); // da chi viene la mail
        message.setSubject("CONFERMA REGISTRAZIONE AppAziendale"); // il TITOLO!
        message.setText("Ciao! Clicca su questo link per confermare la registrazione! " + confirmationUrl); // il testo!
        return message; // ritorno il messaggio
    }

    private SimpleMailMessage createChangePasswordEmail(String email, String forcePasswordUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // a chi mando la mail
        message.setReplyTo("crinq98@gmail.com"); // a chi rispondo se faccio "rispondi"
        message.setFrom("crinq98@gmail.com"); // da chi viene la mail
        message.setSubject("CONFERMA IL CAMBIO DELLA PASSWORD per AppAziendale"); // il TITOLO!
        message.setText("Ciao! Clicca su questo link per confermare il cambio della tua password! " + forcePasswordUrl); // il testo!
        return message; // ritorno il messaggio
    }

    public GenericResponse confirmRegistration(String token) {
        Utente utente = utenteService.getByRegistrationToken(token);
        utente.setRuolo(Role.UTENTE);
        utenteService.insertUtente(utente);
        return GenericResponse
                .builder()
                .message("Account verificato con successo!")
                .build();
    }

    public Object changePassword(Long id_utente, ChangePasswordRequest request) {
        Utente utente = utenteService.getById(id_utente);
        if (!passwordEncoder.matches(request.oldPassword(), utente.getPassword())) {
            // se la vecchia password passata non coincide
            return ErrorResponse
                    .builder()
                    .exception("WrongPasswordException")
                    .message("La vecchia password non è corretta")
                    .build();
        }
        utente.setPassword(passwordEncoder.encode(request.newPassword())); // setto la nuova password
        utenteService.insertUtente(utente);
        return GenericResponse
                .builder()
                .message("Password cambiata con successo")
                .build();
    }

    public GenericResponse resetPassword(String email, String newPassword){
        Utente utente= utenteService.getByEmail(email);
        utente.setPassword(newPassword);
        String resetURL = "http://localhost:8080/app/v1/auth/forcePassword?token=" + utente.getRegistrationToken()+"&newPassword="+utente.getPassword();
        javaMailSender.send(createChangePasswordEmail(email, resetURL));
        return GenericResponse
                .builder()
                .message("Email di cambio password inviata con successo!")
                .build();
    }

    public Object resetPasswordFromEmail(String token, String newPassword){
        Utente utente= utenteService.getByRegistrationToken(token);
        if(!utente.getRegistrationToken().equals(token)){
            return ErrorResponse
                    .builder()
                    .exception("EntityNotFoundException")
                    .message("Utente con token "+ token + " non trovato")
                    .build();
        }
        utente.setPassword(passwordEncoder.encode(newPassword));
        utenteService.insertUtente(utente);
        return GenericResponse
                .builder()
                .message("Password cambiata con successo!")
                .build();
    }


}
