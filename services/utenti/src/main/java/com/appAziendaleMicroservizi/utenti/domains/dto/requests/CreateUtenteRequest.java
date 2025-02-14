package com.appAziendaleMicroservizi.utenti.domains.dto.requests;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUtenteRequest(
        @NotBlank(message = "Il nome non può essere blank o null")
        String nome,

        @NotBlank(message = "Il cognome non può essere blank o null")
        String cognome,

        @Email(message = "Email non valida")
        String email,

        @Size(min = 8, message = "Il dato deve contenere almeno 8 caratteri.")
        @Pattern(
                regexp = ".*[A-Z].*",
                message = "Il dato deve contenere almeno una lettera maiuscola."
        )
        @NotBlank(message = "La password non può essere blank o null")
        String password,

        @Past(message = "La data di nascita deve essere nel passato")
        LocalDate dataNascita,

        @NotBlank(message = "Il luogonascita non può essere blank o null")
        String luogoNascita,

        @Pattern(
                regexp = "^\\+?[0-9]+$",
                message = "Telefono non valido")
        String telefono,

        @NotBlank(message = "L'indirizzo non può essere null o blank")
        String indirizzo,

        @NotBlank(message = "L'immagine non può essere vuota")
        String imgUtente,

        @NotNull(message = "La posizione lavorativa deve essere presente")
        Long idPosizioneLavorativa
) {
}
