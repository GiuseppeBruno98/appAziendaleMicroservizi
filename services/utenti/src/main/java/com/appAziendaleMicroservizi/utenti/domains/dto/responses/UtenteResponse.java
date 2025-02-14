package com.appAziendaleMicroservizi.utenti.domains.dto.responses;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UtenteResponse(Long id,
                             String nome,
                             String cognome,
                             String email,
                             String password,
                             LocalDate dataNascita,
                             String luogoNascita,
                             String telefono,
                             String indirizzo,
                             String ruolo,
                             String imgUtente,
                             Long idPosizioneLavorativa
) {

}
