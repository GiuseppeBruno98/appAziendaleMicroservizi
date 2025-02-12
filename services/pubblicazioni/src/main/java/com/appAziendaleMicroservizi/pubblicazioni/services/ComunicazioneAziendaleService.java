package com.appAziendaleMicroservizi.pubblicazioni.services;

import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateComunicazioneAziendaleRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.UpdateComunicazioneAziendaleRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.ComunicazioneAziendaleResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.ComunicazioneAziendale;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.kafka.PubblicazioneConfirmation;
import com.appAziendaleMicroservizi.pubblicazioni.kafka.PubblicazioneProducer;
import com.appAziendaleMicroservizi.pubblicazioni.mappers.ComunicazioneAziendaleMapper;
import com.appAziendaleMicroservizi.pubblicazioni.repositories.ComunicazioneAziendaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComunicazioneAziendaleService {

    @Autowired
    private ComunicazioneAziendaleRepository comunicazioneAziendaleRepository;

    @Autowired
    private ComunicazioneAziendaleMapper comunicazioneAziendaleMapper;

    @Autowired
    private UtenteClient utenteClient;

    @Autowired
    private UtenteClient2 utenteClient2;

    @Autowired
    private PubblicazioneProducer pubblicazioneProducer;

    public ComunicazioneAziendale getById(Long id) throws MyEntityNotFoundException {
        return comunicazioneAziendaleRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("Comunicazione con id " + id + " non trovata"));
    }

    public ComunicazioneAziendaleResponse getByIdWithResponse(Long id) throws MyEntityNotFoundException {
        return comunicazioneAziendaleMapper.toComunicazioneAziendaleResponse(comunicazioneAziendaleRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("l'utente con id " + id + " non esiste!")));
    }

    public List<ComunicazioneAziendale> getAll() {
        return comunicazioneAziendaleRepository.findAll();
    }

    public List<ComunicazioneAziendaleResponse> getAllWithResponse() {
        return comunicazioneAziendaleRepository.findAll()
                .stream()
                .map(id -> {
                    try {
                        return comunicazioneAziendaleMapper.toComunicazioneAziendaleResponse(id);
                    } catch (MyEntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public EntityIdResponse create(CreateComunicazioneAziendaleRequest request) throws MyEntityNotFoundException {
        var utente = utenteClient.getUtenteResponseById(request.creatorId());
        var utente2 = utenteClient2.getUtenteResponseById(request.creatorId());
        ComunicazioneAziendale savedComunicazione = comunicazioneAziendaleRepository.save(comunicazioneAziendaleMapper.fromCreateComunicazioneAziendaleRequest(request));
        pubblicazioneProducer.sendConfermaPubblicazione(PubblicazioneConfirmation
                .builder()
                .id(savedComunicazione.getId().toString())
                .titolo(savedComunicazione.getTitolo())
                .contenuto(savedComunicazione.getContenuto())
                .timestamp(LocalDateTime.now())
                .build()
        );

        return new EntityIdResponse(savedComunicazione.getId());
    }

    public EntityIdResponse updateComunicazione(Long id, UpdateComunicazioneAziendaleRequest request) throws MyEntityNotFoundException {
        ComunicazioneAziendale comunicazione = getById(id);
        if (request.titolo() != null) comunicazione.setTitolo(request.titolo());
        if (request.contenuto() != null) comunicazione.setContenuto(request.contenuto());
        return new EntityIdResponse(comunicazioneAziendaleRepository.save(comunicazione).getId());
    }

    public void deleteById(Long id) throws MyEntityNotFoundException {
        if (!comunicazioneAziendaleRepository.existsById(id)) {
            throw new MyEntityNotFoundException("Comunicazione con id " + id + " non trovata");
        }
        comunicazioneAziendaleRepository.deleteById(id);
    }
}