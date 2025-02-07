package com.appAziendaleMicroservizi.timbri.domains.dto.responses;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record TimbriResponse(

        LocalDateTime oraInzio,

        Long utenteId,

        LocalTime oraFine,

        LocalTime inizioPausa,

        LocalTime finePausa




) {
}
