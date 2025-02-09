package com.appAziendaleMicroservizi.utente.mappers;


import com.appAziendaleMicroservizi.utente.domains.dto.requests.DipartimentoRequest;
import com.appAziendaleMicroservizi.utente.domains.entities.Dipartimento;
import com.appAziendaleMicroservizi.utente.exceptions.MyEntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DipartimentoMapper {

    public Dipartimento fromCreateDipartimentoRequest(DipartimentoRequest request) throws MyEntityNotFoundException {
        return Dipartimento
                .builder()
                .nome(request.nome())
                .descrizione(request.descrizione())
                .build();
    }

}
