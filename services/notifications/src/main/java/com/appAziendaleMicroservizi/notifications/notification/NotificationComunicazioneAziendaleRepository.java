package com.appAziendaleMicroservizi.notifications.notification;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationComunicazioneAziendaleRepository extends MongoRepository<NotificationComunicazioneAziendale, String> {
}
