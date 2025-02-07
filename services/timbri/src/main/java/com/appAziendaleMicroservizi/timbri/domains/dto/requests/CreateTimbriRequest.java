package com.appAziendaleMicroservizi.timbri.domains.dto.requests;

import com.appAziendaleMicroservizi.timbri.domains.dto.responses.EntityIdResponse;
import jakarta.validation.constraints.NotNull;

public record CreateTimbriRequest(

    @NotNull(message = "L'utente deve essere presente")
    EntityIdResponse utenteId

    ){

}
