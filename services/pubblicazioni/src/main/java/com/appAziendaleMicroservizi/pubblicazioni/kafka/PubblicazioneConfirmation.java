package com.appAziendaleMicroservizi.pubblicazioni.kafka;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PubblicazioneConfirmation(
        Long id,
        String titolo,
        String contenuto,
        LocalDateTime timestamp
) {
}
