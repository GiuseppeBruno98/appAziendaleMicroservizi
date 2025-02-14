package com.appAziendaleMicroservizi.pubblicazioni.kafka;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NewsConfirmation(
        Long id,
        String titolo,
        String contenuto,
        String immagine,
        LocalDateTime timestamp
) {
}
