package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateNewsRequest(
        @NotBlank(message = "Il titolo non può essere blank o null")
        String titolo,

        @NotBlank(message = "Il contenuto non può essere blank o null")
        String contenuto,

        @NotBlank(message = "L'immagine non può essere blank o null")
        String immagine,

        @NotNull(message = "L'id del creatore deve essere presente")
        Long creatorId
) {
}