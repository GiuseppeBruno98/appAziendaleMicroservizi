package com.appAziendaleMicroservizi.notifications.kafka;

import com.appAziendaleMicroservizi.notifications.kafka.pubblicazioni.PubblicazioneConfirmation;
import com.appAziendaleMicroservizi.notifications.notification.Notification;
import com.appAziendaleMicroservizi.notifications.notification.NotificationRepository;
import com.appAziendaleMicroservizi.notifications.notification.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "pubblicazione-topic")
    public void consumePubblicazioneNotification(PubblicazioneConfirmation pubblicazioneConfirmation) {
        System.out.println("ciao");
        notificationRepository.save(
                Notification
                        .builder()
                        .notificationType(NotificationType.PUBBLICAZIONE_NOTIFICATION)
                        .notificationTime(LocalDateTime.now())
                        .pubblicazioneConfirmation(pubblicazioneConfirmation)
                        .build()
        );
        // TODO inviare la email di conferma pubblicazione
    }
}
