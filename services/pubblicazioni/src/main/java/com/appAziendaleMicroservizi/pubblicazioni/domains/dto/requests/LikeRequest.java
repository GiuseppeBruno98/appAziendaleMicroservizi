package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

import jakarta.validation.constraints.NotNull;

public record LikeRequest (

        @NotNull(message = "L'id del creatore deve essere presente")
        Long creatorId,

        @NotNull(message = "L'id della news deve essere presente")
        Long newsId
) {
}
