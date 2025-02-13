package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentoELikeRequest (

    @NotBlank(message = "il commento non deve essere blank o null")
    String commento,

    @NotNull(message = "L'id del creatore deve essere presente")
    Long creatorId,

    @NotNull(message = "L'id della news deve essere presente")
    Long newsId
){
}
