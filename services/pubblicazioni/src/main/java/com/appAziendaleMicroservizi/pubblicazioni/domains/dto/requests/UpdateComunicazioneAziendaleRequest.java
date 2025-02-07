package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests;

public record UpdateComunicazioneAziendaleRequest(
        String titolo,
        String contenuto
) {
}
