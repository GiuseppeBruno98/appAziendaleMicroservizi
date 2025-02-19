package com.appAziendaleMicroservizi.api_gateway.services;


import com.appAziendaleMicroservizi.api_gateway.domains.dto.responses.UtenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "utenti-service", url = "${application.config.utenti-url}")
public interface UtenteClient {

    @GetMapping("/getResponseByEmail")
    UtenteResponse getUtenteResponseByEmail(@RequestBody String email);


}

