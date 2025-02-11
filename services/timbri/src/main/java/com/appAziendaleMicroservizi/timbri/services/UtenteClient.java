package com.appAziendaleMicroservizi.timbri.services;

import com.appAziendaleMicroservizi.timbri.domains.dto.responses.UtenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "utenti-service", url = "${application.config.utenti-url}")
public interface UtenteClient {

    @GetMapping("/getResponse/{id}")
    UtenteResponse getUtenteResponseById(@PathVariable Long id);

}

