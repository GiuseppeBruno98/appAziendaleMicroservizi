package com.appAziendaleMicroservizi.utenti.domains.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateComunicazioneAziendaleRequest (
        @NotBlank(message = "Il titolo non può essere blank o null")
        String titolo,

        @NotBlank(message = "Il contenuto non può essere blank o null")
        String contenuto,

        @NotNull(message = "L'id del creatore deve essere presente")
        Long creatorId
){
}
