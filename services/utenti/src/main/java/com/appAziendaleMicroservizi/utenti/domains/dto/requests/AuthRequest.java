package com.appAziendaleMicroservizi.utenti.domains.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthRequest(
        @Email(message = "Email non valida")
        String email,

        @Size(min = 8, message = "Il dato deve contenere almeno 8 caratteri.")
        @Pattern(
                regexp = ".*[A-Z].*",
                message = "Il dato deve contenere almeno una lettera maiuscola."
        )
        @NotBlank(message = "La password non può essere blank o null")
        String password
) {
}
