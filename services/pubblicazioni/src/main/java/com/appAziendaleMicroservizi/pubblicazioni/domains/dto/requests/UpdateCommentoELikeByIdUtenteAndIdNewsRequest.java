package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

import jakarta.validation.constraints.NotNull;

public record UpdateCommentoELikeByIdUtenteAndIdNewsRequest(
        @NotNull(message = "il creatorId non deve essere null")
        Long idCreator,
        @NotNull(message = "il newsId non deve essere null")
        Long idNews,
        @NotNull(message = "la request non deve essere null")
        UpdateCommentoELikeRequest req
) {
}
