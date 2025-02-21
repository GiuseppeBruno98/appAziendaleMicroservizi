package com.appAziendaleMicroservizi.api_gateway.domains.dto.responses;

import lombok.Builder;

@Builder
public record EntityIdResponse(
        Long id
) {
}
