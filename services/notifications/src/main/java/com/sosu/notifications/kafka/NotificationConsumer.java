package com.sosu.notifications.kafka;

import com.sosu.notifications.kafka.pubblicazioni.PubblicazioneConfirmation;
import com.sosu.notifications.notification.Notification;
import com.sosu.notifications.notification.NotificationRepository;
import com.sosu.notifications.notification.NotificationType;
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
        notificationRepository.save(
                Notification
                        .builder()
                        .notificationType(NotificationType.PUBBLICAZIONE_NOTIFICATION)
                        .notificationTime(LocalDateTime.now())
                        .pubblicazioneConfirmation(pubblicazioneConfirmation)
                        .build()
        );
        // TODO inviare la email di conferma dell'ordine
    }
}
