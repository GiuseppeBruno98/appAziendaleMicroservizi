package com.appAziendaleMicroservizi.notifications.notification;

import com.appAziendaleMicroservizi.notifications.kafka.pubblicazioni.NewsConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class NotificationNews {

    @Id
    private String id;

    private LocalDateTime notificationTime;

    private NewsConfirmation newsConfirmation;

    private NotificationType notificationType;

}
