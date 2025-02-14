package com.appAziendaleMicroservizi.utenti.domains.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PosizioneLavorativaRequest(
        @NotBlank(message = "Il nome non può essere blank o null")
        String nome,

        @NotBlank(message = "La descrizione non può essere blank o null")
        String descrizione,

        @NotNull(message = "L'id del dipartimento deve essere presente")
        Long idDipartimento
) {

}
