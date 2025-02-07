package com.appAziendaleMicroservizi.timbri.mappers;



import com.appAziendaleMicroservizi.timbri.domains.dto.requests.CreateTimbriRequest;
import com.appAziendaleMicroservizi.timbri.domains.dto.responses.TimbriResponse;
import com.appAziendaleMicroservizi.timbri.domains.entities.Timbri;
import com.appAziendaleMicroservizi.timbri.domains.exceptions.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimbriMapper {

    /*@Autowired
    UtenteService utenteService ;*/


    public Timbri fromCreateTimbriRequest(CreateTimbriRequest request/*, Utente utente*/) throws MyEntityNotFoundException {

        return Timbri
                .builder()
                //.utenteId(utente) // Passa l'oggetto Utente
                .oraInizio(LocalDateTime.now()) // Imposta l'ora di inizio
                .build();
    }


    public TimbriResponse toTimbriResponse(Timbri request) throws MyEntityNotFoundException {
        return TimbriResponse
                .builder()
                //.utenteId(request.getUtenteId().getId())
                .oraInzio(request.getOraInizio())
                .oraFine(request.getOraFine())
                .inizioPausa(request.getInizioPausa())
                .finePausa(request.getFinePausa())
                .build();
    }




}
