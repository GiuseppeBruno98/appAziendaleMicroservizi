package com.appAziendaleMicroservizi.timbri.domains.dto.responses;

import lombok.Builder;

@Builder
public record EntityIdResponse (
        Long id
) {
}
