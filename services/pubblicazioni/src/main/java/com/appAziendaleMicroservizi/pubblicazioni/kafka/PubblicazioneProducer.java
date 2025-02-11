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
    private KafkaTemplate<String, PubblicazioneConfirmation> kafkaTemplate;

    public void sendConfermaPubblicazione(PubblicazioneConfirmation pubblicazioneConfirmation) {
        Message<PubblicazioneConfirmation> message = MessageBuilder
                .withPayload(pubblicazioneConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "pubblicazione-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
