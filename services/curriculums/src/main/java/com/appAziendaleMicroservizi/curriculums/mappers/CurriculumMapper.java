package com.appAziendaleMicroservizi.curriculums.mappers;

import com.appAziendaleMicroservizi.curriculums.domains.dto.requests.CreateCurriculumRequest;
import com.appAziendaleMicroservizi.curriculums.domains.entities.Curriculum;
import com.appAziendaleMicroservizi.curriculums.domains.exceptions.MyEntityNotFoundException;

public class CurriculumMapper {
    public Curriculum fromCreateCurriculumRequest(CreateCurriculumRequest request) throws MyEntityNotFoundException {
        return Curriculum
                .builder()
                .idUtente(request.idUtente())
                .path(request.path())
                .build();
    }
}
