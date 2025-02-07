package com.appAziendaleMicroservizi.timbri.services;


import com.appAziendaleMicroservizi.timbri.domains.dto.requests.CreateTimbriRequest;
import com.appAziendaleMicroservizi.timbri.domains.dto.responses.TimbriResponse;
import com.appAziendaleMicroservizi.timbri.domains.entities.Timbri;
import com.appAziendaleMicroservizi.timbri.domains.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.timbri.mappers.TimbriMapper;
import com.appAziendaleMicroservizi.timbri.repositories.TimbriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimbriService {

    //tutte gli @Autowired

    @Autowired
    TimbriRepository timbriRepository;

    @Autowired
    TimbriMapper timbriMapper;

    /*@Autowired
    UtenteService utenteService;*/

    //INIZIO FUNZIONI

    //Funzione per recuperare tramite id

    public Timbri getById(Long id) throws MyEntityNotFoundException {
        return timbriRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("timbro con id " + id + " non trovato"));
    }

    /*public List<TimbriResponse> getResponseByIdUtente(Long id) throws MyEntityNotFoundException {
        Utente utente=utenteService.getById(id);
        List<Timbri> timbri=timbriRepository
                .findByUtenteId(utente);
        List<TimbriResponse> timbriResponses = new ArrayList<>();
        for (Timbri timbro : timbri){
            timbriResponses.add(timbriMapper.toTimbriResponse(timbro));
        }
        return timbriResponses;
    }

    public List<Timbri> getByIdUtente(Long id) throws MyEntityNotFoundException {
        Utente utente=utenteService.getById(id);
        return timbriRepository.findByUtenteId(utente);
    }*/

    public TimbriResponse getByIdWithResponse(Long id) throws MyEntityNotFoundException {
        return timbriMapper.toTimbriResponse(timbriRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("l'utente con id " + id + " non esiste!")));
    }



    public List<Timbri> getAll() {
        return timbriRepository.findAll();
    }

    //Create per i timbri
    /*public Object createTimbri(CreateTimbriRequest request) throws MyEntityNotFoundException {
        // Recupera l'utente dal database usando il service
        Utente utente = utenteService.getById(request.utenteId().id());

        // Usa il mapper per costruire l'entità Timbri
        Timbri timbro = timbriMapper.fromCreateTimbriRequest(request, utente);
        List<Timbri> timbri= getByIdUtente(utente.getId());

        // Salva l'entità nel database
        if(timbri
                .stream()
                .filter(t -> t.getOraInizio().toLocalDate()!=timbro.getOraInizio().toLocalDate())
                .toList()
                .isEmpty()){
            Timbri timbriSaved = timbriRepository.save(timbro);
            return new GenericResponse("Timbro per l'utenteId "+ timbriSaved.getUtenteId().getId() + " creato con successo");
        }else return new ErrorResponse("IllegalArgumentException","Timbro per l'utenteId "+ request.utenteId().id() + " è stato gia' creato");

    }*/

    //Delete per i timbri
    public void deleteById(Long id) {timbriRepository.deleteById(id);
    }

    /*public Object inizioPausa(Long idUtente) throws MyEntityNotFoundException {
        Utente utente = utenteService.getById(idUtente);
        // Recupera il timbro dal database
        List<Timbri> timbri = timbriRepository.findByUtenteId(utente)
                .stream()
                .filter(t -> t.getOraInizio().toLocalDate().isEqual(LocalDate.now()))
                .toList();
        if (timbri.isEmpty()) {
            return new ErrorResponse("IllegalArgumentException","l'utenteId " + utente.getId() + " non ha iniziato a lavorare oggi");
        }
        Timbri timbro=timbri.getFirst();
        if (timbro.getOraFine() == null) {
            if (timbro.getInizioPausa() == null) {
                // Aggiorna il timbro con i nuovi valori
                timbro.setInizioPausa(LocalTime.now());
                // Salva il timbro aggiornato
                Timbri updatedTimbro = timbriRepository.save(timbro);
                // Restituisce la risposta
                return new GenericResponse("Pausa iniziata per l'utenteId "+ updatedTimbro.getUtenteId().getId());
            } else return new ErrorResponse("IllegalArgumentException","l'utenteId " + utente.getId() + "non puo iniziare di nuovo la pausa");
        } else return new ErrorResponse("IllegalArgumentException","l'utenteId " + utente.getId() + " ha già finito di lavorare oggi, non puo fare pausa");
    }
    public Object finePausa(Long idUtente) throws MyEntityNotFoundException {
        Utente utente = utenteService.getById(idUtente);
        // Recupera il timbro dal database
        List<Timbri> timbri = timbriRepository.findByUtenteId(utente)
                .stream()
                .filter(t -> t.getOraInizio().toLocalDate().isEqual(LocalDate.now()))
                .toList();
        if (timbri.isEmpty()) {
            return new ErrorResponse("IllegalArgumentException","l'utenteId " + utente.getId() + " non ha iniziato a lavorare oggi");
        }
        Timbri timbro=timbri.getFirst();
        if (timbro.getInizioPausa() != null) {
            if (timbro.getFinePausa() == null) {
                // Aggiorna il timbro con i nuovi valori
                timbro.setFinePausa(LocalTime.now());
                // Salva il timbro aggiornato
                Timbri updatedTimbro = timbriRepository.save(timbro);
                // Restituisce la risposta
                return new GenericResponse("Pausa iniziata per l'utenteId "+ updatedTimbro.getUtenteId().getId());
            } else return new ErrorResponse("IllegalArgumentException","l'utenteId" + utente.getId() +"non puo' finire di nuovo la pausa");
        } else return new ErrorResponse("IllegalArgumentException","l'utenteId" + utente.getId() +" non ha mai iniziato la pausa");

    }
    public Object fine(Long idUtente) throws MyEntityNotFoundException {
        Utente utente = utenteService.getById(idUtente);
        System.out.println(utente);
        // Recupera il timbro dal database
        List<Timbri> timbri = timbriRepository.findByUtenteId(utente)
                .stream()
                .filter(t -> t.getOraInizio().toLocalDate().isEqual(LocalDate.now()))
                .toList();
        if (timbri.isEmpty()) {
            return new ErrorResponse("IllegalArgumentException","l'utenteId " + utente.getId() + " non ha iniziato a lavorare oggi");
        }
        Timbri timbro=timbri.getFirst();
        if (timbro.getOraFine() == null) {
            if (timbro.getInizioPausa() == null || timbro.getFinePausa() != null) {
                // Aggiorna il timbro con i nuovi valori
                timbro.setOraFine(LocalTime.now());
                // Salva il timbro aggiornato
                Timbri updatedTimbro = timbriRepository.save(timbro);
                // Restituisce la risposta
                return new GenericResponse("Fine del lavoro per l'utenteId "+ updatedTimbro.getUtenteId().getId());
            } else return new ErrorResponse("IllegalArgumentException","l'utenteId " + utente.getId() +" non puo' finire il lavoro mentre e' in pausa");
        } else return new ErrorResponse("IllegalArgumentException","l'utenteId " + utente.getId() +" non puo finire di nuovo il lavoro");
    }*/

}
