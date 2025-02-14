package com.appAziendaleMicroservizi.pubblicazioni.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PubblicazioneProducer {

    @Autowired
    private KafkaTemplate<String, ComunicazioneAziendaleConfirmation> kafkaTemplate;

    public void sendConfermaPubblicazioneComunicazioneAziendale(ComunicazioneAziendaleConfirmation comunicazioneAziendaleConfirmation) {
        Message<ComunicazioneAziendaleConfirmation> message = MessageBuilder
                .withPayload(comunicazioneAziendaleConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "comunicazioneAziendale-topic")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendConfermaPubblicazioneNews(NewsConfirmation newsConfirmation) {
        Message<NewsConfirmation> message = MessageBuilder
                .withPayload(newsConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "news-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
