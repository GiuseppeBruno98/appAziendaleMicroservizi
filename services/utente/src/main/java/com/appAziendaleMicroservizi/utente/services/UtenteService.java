package com.appAziendaleMicroservizi.utente.services;


import com.appAziendaleMicroservizi.utente.domains.dto.requests.CreateUtenteRequest;
import com.appAziendaleMicroservizi.utente.domains.dto.requests.UpdateUtenteRequest;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.UtenteResponse;
import com.appAziendaleMicroservizi.utente.domains.entities.Utente;
import com.appAziendaleMicroservizi.utente.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.utente.mappers.UtenteMapper;
import com.appAziendaleMicroservizi.utente.repositories.UtenteRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteMapper utenteMapper;

    @Autowired
    private PosizioneLavorativaService posizioneLavorativaService;


    public Utente getById(Long id) throws MyEntityNotFoundException {
        return utenteRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("utente con id " + id + " non trovato"));
    }

    public UtenteResponse getByIdWithResponse(Long id) throws MyEntityNotFoundException {
        return utenteMapper.toUtenteResponse(utenteRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("l'utente con id " + id + " non esiste!")));
    }

    public Utente getByEmail(@Email(message = "Email non valida") String email) {
        return utenteRepository
                .findByEmail(email)
                .orElseThrow(() -> new MyEntityNotFoundException("l'utente con email " + email + " non esiste!"));
    }

    public Utente getByRegistrationToken(String token) {
        return utenteRepository
                .findByRegistrationToken(token)
                .orElseThrow(() -> new MyEntityNotFoundException("utente con token " + token + " non trovato"));
    }

    public List<Utente> getAll() {
        return utenteRepository.findAll();
    }

    public List<UtenteResponse> getAllWithResponse() {
        return utenteRepository.findAll()
                .stream()
                .map(id -> {
                    try {
                        return utenteMapper.toUtenteResponse(id);
                    } catch (MyEntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public EntityIdResponse createUtente(CreateUtenteRequest request) throws MyEntityNotFoundException {
        Utente utenteSaved = utenteRepository.save(utenteMapper.fromCreateUtenteRequest(request));
        return new EntityIdResponse(utenteSaved.getId());
    }

    public void insertUtente(Utente utente) {
        utenteRepository.save(utente);
    }

    public EntityIdResponse updateUtente(Long id, UpdateUtenteRequest request) throws MyEntityNotFoundException {
        Utente myUtente = getById(id);
        if (request.nome() != null) myUtente.setNome(request.nome());
        if (request.cognome()!= null) myUtente.setCognome(request.cognome());
        if (request.password() != null) myUtente.setPassword(request.password());
        if (request.luogoNascita() != null) myUtente.setLuogoNascita(request.luogoNascita());
        if (request.telefono() != null) myUtente.setTelefono(request.telefono());
        if (request.imgUtente() != null) myUtente.setImgUtente(request.imgUtente());
        if (request.idPosizioneLavorativa() != null) {
            myUtente.setIdPosizioneLavorativa(posizioneLavorativaService.getById(request.idPosizioneLavorativa().id()));
        }
        return new EntityIdResponse(utenteRepository.save(myUtente).getId());
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }
}
