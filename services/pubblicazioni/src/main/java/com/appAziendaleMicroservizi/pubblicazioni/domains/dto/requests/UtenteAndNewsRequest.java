package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

public record UtenteAndNewsRequest(
        Long idCreator,
        Long idNews
) {
}
