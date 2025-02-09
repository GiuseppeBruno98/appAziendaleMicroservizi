package com.appAziendaleMicroservizi.pubblicazioni.services;

import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.UtenteResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;


@Service
public class UtenteClient2 {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${application.config.utenti-url}")
    private String utentiUrl;

    public UtenteResponse getUtenteResponseById(@RequestBody Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // imposto come json il contenuto del body
        HttpEntity<Long> requestEntity = new HttpEntity<>(id, headers); // definisco l'entità nel body
        // definisco il tipo di risposta dell'API
        ParameterizedTypeReference<UtenteResponse> responseType =
                new ParameterizedTypeReference<>() {};
        ResponseEntity<UtenteResponse> responseEntity = restTemplate.exchange
                (utentiUrl + "/getResponse/"+ id,
                        HttpMethod.POST, requestEntity, responseType);
        if (responseEntity.getStatusCode().isError()) throw new EntityNotFoundException("Qualcosa è andato storto nel trovare l'utente");
        return responseEntity.getBody();
    }
}
