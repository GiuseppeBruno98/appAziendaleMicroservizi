package com.appAziendaleMicroservizi.notifications.notification;

import com.appAziendaleMicroservizi.notifications.kafka.pubblicazioni.PubblicazioneConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Notification {

    @Id
    private String id;

    private LocalDateTime notificationTime;

    private PubblicazioneConfirmation pubblicazioneConfirmation;

    private NotificationType notificationType;

}
