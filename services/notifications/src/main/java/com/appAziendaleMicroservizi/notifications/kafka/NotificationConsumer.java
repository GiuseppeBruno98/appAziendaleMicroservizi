package com.appAziendaleMicroservizi.notifications.kafka;

import com.appAziendaleMicroservizi.notifications.kafka.pubblicazioni.ComunicazioneAziendaleConfirmation;
import com.appAziendaleMicroservizi.notifications.kafka.pubblicazioni.NewsConfirmation;
import com.appAziendaleMicroservizi.notifications.notification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationComunicazioneAziendaleRepository notificationComunicazioneAziendaleRepository;

    @Autowired
    private NotificationNewsRepository notificationNewsRepository;

    @KafkaListener(topics = "comunicazioneAziendale-topic", groupId = "pubblicazioni-group")
    public void consumePubblicazioneComunicazioneAziendaleNotification(ComunicazioneAziendaleConfirmation comunicazioneAziendaleConfirmation) {
        notificationComunicazioneAziendaleRepository.save(
                NotificationComunicazioneAziendale
                        .builder()
                        .notificationType(NotificationType.COMUNICAZIONE_AZIENDALE_NOTIFICATION)
                        .notificationTime(LocalDateTime.now())
                        .comunicazioneAziendaleConfirmation(comunicazioneAziendaleConfirmation)
                        .build()
        );
        // TODO inviare la email di conferma pubblicazione
    }

    @KafkaListener(topics = "news-topic", groupId = "pubblicazioni-group")
    public void consumePubblicazioneNewsNotification(NewsConfirmation newsConfirmation) {
        notificationNewsRepository.save(
                NotificationNews
                        .builder()
                        .notificationType(NotificationType.NEWS_NOTIFICATION)
                        .notificationTime(LocalDateTime.now())
                        .newsConfirmation(newsConfirmation)
                        .build()
        );
        // TODO inviare la email di conferma pubblicazione
    }
}
