package com.appAziendaleMicroservizi.utenti.domains.dto.responses;

import lombok.Builder;

@Builder
public record EntityIdResponse (
        Long id
) {
}
