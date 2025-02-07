package com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses;

import lombok.Builder;

@Builder
public record GenericResponse(
        String message
) {
}
