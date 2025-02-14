package com.appAziendaleMicroservizi.curriculums.domains.dto.responses;

import lombok.Builder;

@Builder
public record GenericResponse(
        String message
) {
}
