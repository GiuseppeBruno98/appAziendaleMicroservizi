package com.appAziendaleMicroservizi.curriculums.domains.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCurriculumRequest(
        @NotBlank
        String path,
        @NotNull
        Long idUtente
) {
}
