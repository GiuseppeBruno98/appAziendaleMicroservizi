package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses;

import lombok.Builder;


@Builder
public record ComunicazioneAziendaleResponse (
        Long id,
        String titolo,
        String contenuto,
        Long creatorId
    ){
}
