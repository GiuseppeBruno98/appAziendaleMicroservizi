package com.appAziendaleMicroservizi.curriculums.services;

import com.appAziendaleMicroservizi.curriculums.domains.dto.requests.CreateCurriculumRequest;
import com.appAziendaleMicroservizi.curriculums.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.curriculums.domains.entities.Curriculum;
import com.appAziendaleMicroservizi.curriculums.domains.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.curriculums.mappers.CurriculumMapper;
import com.appAziendaleMicroservizi.curriculums.repositories.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private CurriculumMapper curriculumMapper;

    @Autowired
    private UtenteClient utenteClient;

    public Curriculum getById(Long id) throws MyEntityNotFoundException {
        return curriculumRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("il curriculum con id " + id + " non trovato"));
    }

    public Curriculum getByIdUtente(Long idUtente) {
        return curriculumRepository
                .findByUtenteId(idUtente)
                .orElseThrow(() -> new MyEntityNotFoundException("il curriculum per l'utente con id " + idUtente + " non esiste!"));
    }


    public List<Curriculum> getAll() {
        return curriculumRepository.findAll();
    }


    public EntityIdResponse create(CreateCurriculumRequest request) throws MyEntityNotFoundException {
        var utente = utenteClient.getUtenteResponseById(request.idUtente());

        Curriculum newCurriculum= curriculumRepository.save(curriculumMapper.fromCreateCurriculumRequest(request));
        return new EntityIdResponse(newCurriculum.getId());
    }

    /*
    public EntityIdResponse updateUtente(Long id, UpdateUtenteRequest request) throws MyEntityNotFoundException {
        Utente myUtente = getById(id);
        if (request.nome() != null) myUtente.setNome(request.nome());
        if (request.cognome()!= null) myUtente.setCognome(request.cognome());
        if (request.password() != null) myUtente.setPassword(request.password());
        if (request.luogoNascita() != null) myUtente.setLuogoNascita(request.luogoNascita());
        if (request.telefono() != null) myUtente.setTelefono(request.telefono());
        if (request.imgUtente() != null) myUtente.setImgUtente(request.imgUtente());
        if (request.idPosizioneLavorativa() != null) {
            myUtente.setIdPosizioneLavorativa(posizioneLavorativaService.getById(request.idPosizioneLavorativa()));
        }
        return new EntityIdResponse(utenteRepository.save(myUtente).getId());
    }
    */
    public void deleteById(Long id) {
        curriculumRepository.deleteById(id);
    }

}
