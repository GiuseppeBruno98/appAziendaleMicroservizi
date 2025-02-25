package com.appAziendaleMicroservizi.api_gateway.configurations;

import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class FeignWebFluxConfig {

    @Bean
    public Decoder feignDecoder() {
        // Crea un ObjectMapper personalizzato
        ObjectMapper mapper = new ObjectMapper();

        // Registra il modulo per le date/time Java 8
        mapper.registerModule(new JavaTimeModule());

        // Se occorre, disabilita le scritture con array per date
        // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Usa l’ObjectMapper personalizzato
        return new JacksonDecoder(mapper);
    }

    @Bean
    public Encoder feignEncoder() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return new JacksonEncoder(mapper);
    }

    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}