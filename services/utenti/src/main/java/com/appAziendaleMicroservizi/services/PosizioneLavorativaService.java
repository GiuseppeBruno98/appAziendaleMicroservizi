package com.appAziendaleMicroservizi.services;

import com.appAziendaleMicroservizi.domains.dto.requests.PosizioneLavorativaRequest;
import com.appAziendaleMicroservizi.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.domains.dto.responses.PosizioneLavorativaResponse;
import com.appAziendaleMicroservizi.domains.entities.PosizioneLavorativa;
import com.appAziendaleMicroservizi.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.mappers.PosizioneLavorativaMapper;
import com.appAziendaleMicroservizi.repositories.PosizioneLavorativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosizioneLavorativaService {

    @Autowired
    PosizioneLavorativaRepository posizioneLavorativaRepository;

    @Autowired
    PosizioneLavorativaMapper posizioneLavorativaMapper;

    @Autowired
    DipartimentoService dipartimentoService;

    public PosizioneLavorativa getById(Long id) throws MyEntityNotFoundException {
        return posizioneLavorativaRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("posizione lavorativa con id " + id + " non trovato"));
    }

    public PosizioneLavorativaResponse getByIdWithResponse(Long id) throws MyEntityNotFoundException {
        return posizioneLavorativaMapper.toPosizioneLavorativaResponse(posizioneLavorativaRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException(" con id " + id + " non esiste!")));
    }

    public List<PosizioneLavorativa> getAll() {
        return posizioneLavorativaRepository.findAll();
    }
    public List<PosizioneLavorativaResponse> getAllWithResponse() {
        return posizioneLavorativaRepository.findAll()
                .stream()
                .map(id -> {
                    try {
                        return posizioneLavorativaMapper.toPosizioneLavorativaResponse(id);
                    } catch (MyEntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public EntityIdResponse createPosizioneLavorativa(PosizioneLavorativaRequest request) throws MyEntityNotFoundException {
        PosizioneLavorativa posizioneLavorativaSaved = posizioneLavorativaRepository.save(posizioneLavorativaMapper.fromCreatePosizoneLavorativaRequest(request));
        return new EntityIdResponse(posizioneLavorativaSaved.getId());
    }

    public EntityIdResponse updatePosizioneLavorativa(Long id, PosizioneLavorativaRequest request) throws MyEntityNotFoundException {
        PosizioneLavorativa myPosizioneLavorativa = getById(id);
        if (request.nome() != null) myPosizioneLavorativa.setNome(request.nome());
        if (request.descrizione()!= null) myPosizioneLavorativa.setDescrizione(request.descrizione());
        if (request.idDipartimento() != null) {
            myPosizioneLavorativa.setIdDipartimento(dipartimentoService.getById(request.idDipartimento()));
        }
        return new EntityIdResponse(posizioneLavorativaRepository.save(myPosizioneLavorativa).getId());
    }

    public void deleteById(Long id) {
        posizioneLavorativaRepository.deleteById(id);
    }
}
