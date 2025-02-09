package com.appAziendaleMicroservizi.mappers;


import com.appAziendaleMicroservizi.domains.dto.requests.DipartimentoRequest;
import com.appAziendaleMicroservizi.domains.entities.Dipartimento;
import com.appAziendaleMicroservizi.exceptions.MyEntityNotFoundException;
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
