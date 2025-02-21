package com.appAziendaleMicroservizi.utenti.security;

import com.appAziendaleMicroservizi.utenti.domains.dto.requests.AuthRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.requests.ChangePasswordRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.requests.RegisterRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.AuthenticationResponse;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.CREATED);
    }

    @PostMapping("/logout/{utenteId}")
    public ResponseEntity<GenericResponse> logout(@PathVariable Long utenteId, HttpServletRequest request) {
        System.out.println("ciaooooo");
        String token = request.getHeader("Authorization").substring(7);
        return new ResponseEntity<>(authenticationService.logout(utenteId, token), HttpStatus.CREATED);
    }

    @GetMapping("/confirm")
    public ResponseEntity<GenericResponse> confirmRegistration(@RequestParam String token) {
        return new ResponseEntity<>(authenticationService.confirmRegistration(token), HttpStatus.CREATED);
    }

    @PutMapping("/change_pw/{id_utente}")
    public ResponseEntity<?> changePassword(@PathVariable Long id_utente, @RequestBody ChangePasswordRequest request) {
        Object result = authenticationService.changePassword(id_utente, request);
        if (result.getClass() == GenericResponse.class) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    /*@PostMapping("/reset_pw")
    public ResponseEntity<?> resetPassword(@RequestParam String mail, @RequestParam String newPassword) {
        return new ResponseEntity<>(authenticationService.resetPassword(mail, newPassword), HttpStatus.CREATED);
    }

    @GetMapping("/forcePassword")
    public ResponseEntity<?> resetPasswordFromEmail(@RequestParam String token,@RequestParam String newPassword) {
        Object result = authenticationService.resetPasswordFromEmail(token, newPassword);
        if (result.getClass() == GenericResponse.class) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }*/

}
