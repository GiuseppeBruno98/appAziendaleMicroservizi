package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

public record UpdateNewsRequest (
        String titolo,

        String contenuto,

        String immagine
){
}
