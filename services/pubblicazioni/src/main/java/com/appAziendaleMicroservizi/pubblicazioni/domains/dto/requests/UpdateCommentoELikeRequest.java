package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentoELikeRequest (

        @NotBlank(message = "il commento non deve essere blank o null")
        String commento
){
}
