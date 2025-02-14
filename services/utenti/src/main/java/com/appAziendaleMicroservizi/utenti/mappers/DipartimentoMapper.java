package com.appAziendaleMicroservizi.utenti.mappers;


import com.appAziendaleMicroservizi.utenti.domains.dto.requests.DipartimentoRequest;
import com.appAziendaleMicroservizi.utenti.domains.entities.Dipartimento;
import com.appAziendaleMicroservizi.utenti.exceptions.MyEntityNotFoundException;
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
