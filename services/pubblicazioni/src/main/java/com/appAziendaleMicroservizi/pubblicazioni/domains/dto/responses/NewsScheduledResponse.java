package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record NewsScheduledResponse(
        Long id,
        String titolo,
        String contenuto,
        String immagine,
        Long creatorId,
        LocalDateTime publishTime
) {
}
