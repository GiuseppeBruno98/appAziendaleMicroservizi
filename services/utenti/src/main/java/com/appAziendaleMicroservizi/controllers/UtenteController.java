package com.appAziendaleMicroservizi.controllers;

import com.appAziendaleMicroservizi.domains.dto.requests.CreateComunicazioneAziendaleRequest;
import com.appAziendaleMicroservizi.domains.dto.requests.CreateUtenteRequest;
import com.appAziendaleMicroservizi.domains.dto.requests.UpdateUtenteRequest;
import com.appAziendaleMicroservizi.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.domains.dto.responses.UtenteResponse;
import com.appAziendaleMicroservizi.domains.entities.Utente;
import com.appAziendaleMicroservizi.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(utenteService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getResponse/{id}")
    public ResponseEntity<UtenteResponse> getByIdWithResponse(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(utenteService.getByIdWithResponse(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Utente>> getAll() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/allResponse")
    public ResponseEntity<List<UtenteResponse>> getAllwithResponse() {
        return new ResponseEntity<>(utenteService.getAllWithResponse(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestBody @Valid CreateUtenteRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(utenteService.createUtente(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateUtenteRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(utenteService.updateUtente(id, request), HttpStatus.CREATED);
    }

    @PostMapping("/createComunicazioneAziendale")
    public ResponseEntity<EntityIdResponse> createComunicazioneAziendale(@RequestBody @Valid CreateComunicazioneAziendaleRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(utenteService.createComunicazioneAziendale(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) {
        utenteService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("Utente con id " + id + " eliminato correttamente"), HttpStatus.OK);
    }


}
