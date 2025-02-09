package com.appAziendaleMicroservizi.domains.dto.responses;

import lombok.Builder;

@Builder
public record EntityIdResponse (
        Long id
) {
}
