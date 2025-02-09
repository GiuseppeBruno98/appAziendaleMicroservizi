package com.appAziendaleMicroservizi.domains.dto.responses;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String exception,
        String message
) {
}
