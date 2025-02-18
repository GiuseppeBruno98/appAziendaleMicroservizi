package com.appAziendaleMicroservizi.timbri.controllers;

import com.appAziendaleMicroservizi.timbri.domains.dto.requests.MailRequest;
import com.appAziendaleMicroservizi.timbri.domains.dto.responses.TimbriResponse;
import com.appAziendaleMicroservizi.timbri.domains.exceptions.MyEntityNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/mail")
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send")
    public ResponseEntity<String> getById(@RequestBody MailRequest to) throws MessagingException {
        try {

            // Verifica se l'indirizzo email è valido
            if (!isValidEmail(to.email())) {
                return new ResponseEntity<>("Indirizzo email non valido: " + to, HttpStatus.BAD_REQUEST);
            }

            // Creazione e configurazione del messaggio email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Impostazione dell'indirizzo destinatario
            helper.setTo(to.email());
            helper.setSubject("Test Email da MailDev");
            helper.setText("Questa è una email di test inviata tramite MailDev e Spring Boot.", true);

            // Invio della mail
            mailSender.send(message);

            // Risposta di successo
            return new ResponseEntity<>("Email inviata con successo a: " + to, HttpStatus.OK);
        } catch (AddressException e) {
            // Gestione errore se l'indirizzo email non è valido
            return new ResponseEntity<>("Errore nell'indirizzo email: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MessagingException e) {
            // Gestione di altri errori di invio email
            return new ResponseEntity<>("Errore nell'invio dell'email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Gestione di qualsiasi altro errore imprevisto
            return new ResponseEntity<>("Errore imprevisto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidEmail(String email) {
        try {
            new InternetAddress(email).validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

}
