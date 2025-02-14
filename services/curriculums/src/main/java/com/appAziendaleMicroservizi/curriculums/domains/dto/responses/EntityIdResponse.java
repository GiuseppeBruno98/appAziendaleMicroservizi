package com.appAziendaleMicroservizi.curriculums.domains.dto.responses;

import lombok.Builder;

@Builder
public record EntityIdResponse (
        Long id
) {
}
