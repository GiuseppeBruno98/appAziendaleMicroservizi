package com.appAziendaleMicroservizi.utente.services;

import com.appAziendaleMicroservizi.utente.domains.dto.requests.DipartimentoRequest;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.utente.domains.entities.Dipartimento;
import com.appAziendaleMicroservizi.utente.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.utente.mappers.DipartimentoMapper;
import com.appAziendaleMicroservizi.utente.repositories.DipartimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DipartimentoService {

    @Autowired
    DipartimentoRepository dipartimentoRepository;

    @Autowired
    DipartimentoMapper dipartimentoMapper;

    public Dipartimento getById(Long id) throws MyEntityNotFoundException {
        return dipartimentoRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Dipartimento con id " + id + " non trovato"));
    }

    public List<Dipartimento> getAll() {
        return dipartimentoRepository.findAll();
    }

    public EntityIdResponse createDipartimento(DipartimentoRequest request) throws MyEntityNotFoundException {
        Dipartimento dipartimentoSaved = dipartimentoRepository.save(dipartimentoMapper.fromCreateDipartimentoRequest(request));
        return new EntityIdResponse(dipartimentoSaved.getId());
    }

    public EntityIdResponse updateDipartimento(Long id, DipartimentoRequest request) throws MyEntityNotFoundException {
        Dipartimento myDipartimento = getById(id);
        if (request.nome() != null) myDipartimento.setNome(request.nome());
        if (request.descrizione()!= null) myDipartimento.setDescrizione(request.descrizione());
        return new EntityIdResponse(dipartimentoRepository.save(myDipartimento).getId());
    }

    public void deleteById(Long id) {
        dipartimentoRepository.deleteById(id);
    }
}
