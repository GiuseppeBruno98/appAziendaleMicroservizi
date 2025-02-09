package com.appAziendaleMicroservizi.domains.dto.requests;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUtenteRequest(
        String nome,
        String cognome,

        @Size(min = 8, message = "Il dato deve contenere almeno 8 caratteri.")
        @Pattern(
                regexp = ".*[A-Z].*",
                message = "Il dato deve contenere almeno una lettera maiuscola."
        )
        String password,

        String luogoNascita,

        @Pattern(
                regexp = "^\\+?[0-9]+$",
                message = "Telefono non valido")
        String telefono,

        String imgUtente,

        EntityIdRequest idPosizioneLavorativa
) {
}
