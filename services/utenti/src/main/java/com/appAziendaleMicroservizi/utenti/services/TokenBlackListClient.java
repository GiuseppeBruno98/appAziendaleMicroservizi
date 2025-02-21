package com.appAziendaleMicroservizi.utenti.services;

import com.appAziendaleMicroservizi.utenti.domains.dto.responses.EntityIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "token-blackList-service", url = "${application.config.token-blackList-url}")
public interface TokenBlackListClient {

    @PostMapping("/insert")
    EntityIdResponse insertToken(@RequestParam Long idUtente, String token);

}
