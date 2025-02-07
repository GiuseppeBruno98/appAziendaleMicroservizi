package com.appAziendaleMicroservizi.utente.services;

import com.appAziendaleMicroservizi.utente.domains.dto.requests.CreateComunicazioneAziendaleRequest;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.EntityIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "comunicazione-aziendale-service", url = "${application.config.comunicazione-aziendale-url}")
public interface ComunicazioneAziendaleClient {

    @PostMapping("/create")
    EntityIdResponse createComunicazioneAziendale(@RequestBody CreateComunicazioneAziendaleRequest request);

}
