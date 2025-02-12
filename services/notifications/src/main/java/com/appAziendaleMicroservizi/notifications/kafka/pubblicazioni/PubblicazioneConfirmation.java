package com.appAziendaleMicroservizi.notifications.kafka.pubblicazioni;

import com.sosu.notifications.kafka.payment.PaymentMethod;
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
