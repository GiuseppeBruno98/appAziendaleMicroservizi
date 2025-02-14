package com.appAziendaleMicroservizi.pubblicazioni.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.List;

@Configuration
public class KafkaPubblicazioneTopicConfig {

    @Bean
    public List<NewTopic> pubblicazioneTopic() {
        return List.of(
                TopicBuilder.name("news-topic").build(),
                TopicBuilder.name("comunicazioneAziendale-topic").build()
                );
    }

}
