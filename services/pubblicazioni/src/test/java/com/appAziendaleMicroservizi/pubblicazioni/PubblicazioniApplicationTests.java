package com.appAziendaleMicroservizi.pubblicazioni;

import com.appAziendaleMicroservizi.pubblicazioni.kafka.PubblicazioneConfirmation;
import com.appAziendaleMicroservizi.pubblicazioni.kafka.PubblicazioneProducer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PubblicazioniApplicationTests {

	@Mock
	private KafkaTemplate<String, PubblicazioneConfirmation> kafkaTemplate;

	@InjectMocks
	private PubblicazioneProducer pubblicazioneProducer;

	@Test
	void testpippo(){
		PubblicazioneConfirmation pubblicazioneConfirmation= PubblicazioneConfirmation
				.builder()
				.id("5")
				.titolo("titolo")
				.timestamp(LocalDateTime.now())
				.contenuto("cosebelle")
				.build();
		Message<PubblicazioneConfirmation> message = MessageBuilder
				.withPayload(pubblicazioneConfirmation)
				.setHeader(KafkaHeaders.TOPIC, "pubblicazione-topic")
				.build();
		kafkaTemplate.send(message);
		verify(kafkaTemplate,times(1)).send(any(Message.class));
	}

}
