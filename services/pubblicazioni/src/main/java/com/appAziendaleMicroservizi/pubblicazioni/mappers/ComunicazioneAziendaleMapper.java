package com.appAziendaleMicroservizi.pubblicazioni.mappers;

import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateComunicazioneAziendaleRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateComunicazioneAziendaleScheduledRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.ComunicazioneAziendaleResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.ComunicazioneAziendaleScheduledResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.ComunicazioneAziendale;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.ComunicazioneAziendaleScheduled;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ComunicazioneAziendaleMapper {

    public ComunicazioneAziendaleScheduled fromCreateComunicazioneAziendaleScheduledRequestToComunicazioneAziendaleScheduled(CreateComunicazioneAziendaleScheduledRequest request) throws MyEntityNotFoundException {
        return ComunicazioneAziendaleScheduled.builder()
                .titolo(request.titolo())
                .contenuto(request.contenuto())
                .publishTime(request.publishTime())
                .creatorId(request.creatorId())
                .build();
    }

    public CreateComunicazioneAziendaleRequest fromCreateComunicazioneAziendaleScheduledRequestToCreateComunicazioneAziendaleRequest(CreateComunicazioneAziendaleScheduledRequest request) throws MyEntityNotFoundException {
        return CreateComunicazioneAziendaleRequest.builder()
                .titolo(request.titolo())
                .contenuto(request.contenuto())
                .creatorId(request.creatorId())
                .build();
    }

    public ComunicazioneAziendale fromCreateComunicazioneAziendaleRequest(CreateComunicazioneAziendaleRequest request) throws MyEntityNotFoundException {
        return ComunicazioneAziendale
                .builder()
                .titolo(request.titolo())
                .contenuto(request.contenuto())
                .creatorId(request.creatorId())
                .build();
    }


    public ComunicazioneAziendaleResponse toComunicazioneAziendaleResponse(ComunicazioneAziendale request) throws MyEntityNotFoundException {
        return ComunicazioneAziendaleResponse
                .builder()
                .id(request.getId())
                .titolo(request.getTitolo())
                .contenuto(request.getContenuto())
                .creatorId(request.getId())
                .build();
    }

    public ComunicazioneAziendaleScheduledResponse toComunicazioneAziendaleScheduledResponse(ComunicazioneAziendaleScheduled request) throws MyEntityNotFoundException  {
        return ComunicazioneAziendaleScheduledResponse.builder()
                .id(request.getId())
                .titolo(request.getTitolo())
                .contenuto(request.getContenuto())
                .publishTime(request.getPublishTime())
                .creatorId(request.getCreatorId())
                .build();
    }

 }



