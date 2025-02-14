package com.appAziendaleMicroservizi.utenti.mappers;

import com.appAziendaleMicroservizi.utenti.domains.dto.requests.PosizioneLavorativaRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.PosizioneLavorativaResponse;
import com.appAziendaleMicroservizi.utenti.domains.entities.PosizioneLavorativa;
import com.appAziendaleMicroservizi.utenti.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.utenti.services.DipartimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosizioneLavorativaMapper {

    @Autowired
    DipartimentoService dipartimentoService;

    public PosizioneLavorativa fromCreatePosizoneLavorativaRequest(PosizioneLavorativaRequest request) throws MyEntityNotFoundException {
        return PosizioneLavorativa
                .builder()
                .nome(request.nome())
                .descrizione(request.descrizione())
                .idDipartimento(dipartimentoService.getById(request.idDipartimento()))
                .build();
    }

    public PosizioneLavorativaResponse toPosizioneLavorativaResponse(PosizioneLavorativa request) throws MyEntityNotFoundException {
        return PosizioneLavorativaResponse
                .builder()
                .id(request.getId())
                .nome(request.getNome())
                .descrizione(request.getDescrizione())
                .idDipartimento(request.getIdDipartimento().getId())
                .build();
    }
}
