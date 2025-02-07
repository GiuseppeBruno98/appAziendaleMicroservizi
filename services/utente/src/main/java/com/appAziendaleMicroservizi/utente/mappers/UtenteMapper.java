package com.appAziendaleMicroservizi.utente.mappers;


import com.appAziendaleMicroservizi.utente.domains.dto.requests.CreateUtenteRequest;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.UtenteResponse;
import com.appAziendaleMicroservizi.utente.domains.entities.Utente;
import com.appAziendaleMicroservizi.utente.domains.enums.Role;
import com.appAziendaleMicroservizi.utente.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.utente.services.PosizioneLavorativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteMapper {

    @Autowired
    PosizioneLavorativaService posizioneLavorativaService;


    public Utente fromCreateUtenteRequest(CreateUtenteRequest request) throws MyEntityNotFoundException {
        return Utente
                .builder()
                .nome(request.nome())
                .cognome(request.cognome())
                .email(request.email())
                .password(request.password())
                .dataNascita(request.dataNascita())
                .luogoNascita(request.luogoNascita())
                .telefono(request.telefono())
                .indirizzo(request.indirizzo())
                .ruolo(Role.UTENTE)
                .imgUtente(request.imgUtente())
                .idPosizioneLavorativa(posizioneLavorativaService.getById(request.idPosizioneLavorativa().id()))
                .build();
    }

    public UtenteResponse toUtenteResponse(Utente request) throws MyEntityNotFoundException {
        return UtenteResponse
                .builder()
                .id(request.getId())
                .nome(request.getNome())
                .cognome(request.getCognome())
                .email(request.getEmail())
                .password(request.getPassword())
                .dataNascita(request.getDataNascita())
                .luogoNascita(request.getLuogoNascita())
                .telefono(request.getTelefono())
                .indirizzo(request.getIndirizzo())
                .ruolo(request.getRuolo().name())
                .imgUtente(request.getImgUtente())
                .idPosizioneLavorativa(request.getIdPosizioneLavorativa().getId())
                .build();
    }


}
