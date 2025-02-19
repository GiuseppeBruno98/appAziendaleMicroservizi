package com.appAziendaleMicroservizi.api_gateway.domains.dto.responses;

import java.time.LocalDate;

public record UtenteResponse(
        Long id,
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
