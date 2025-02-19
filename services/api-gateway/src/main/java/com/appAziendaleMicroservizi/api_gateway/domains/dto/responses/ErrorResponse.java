package com.appAziendaleMicroservizi.api_gateway.domains.dto.responses;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String exception,
        String message
) {
}
