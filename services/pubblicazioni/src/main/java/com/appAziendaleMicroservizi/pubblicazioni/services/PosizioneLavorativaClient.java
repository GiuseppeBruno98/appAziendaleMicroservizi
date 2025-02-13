package com.appAziendaleMicroservizi.pubblicazioni.services;

import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.PosizioneLavorativaResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.UtenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "posizioneLavorativa-service", url = "${application.config.posizioniLavorative-url}")
public interface PosizioneLavorativaClient {

    @GetMapping("/getResponse/{id}")
    PosizioneLavorativaResponse getPosizioneLavorativaResponseById(@PathVariable Long id);

}

