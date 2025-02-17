package com.appAziendaleMicroservizi.utenti.controllers;

import com.appAziendaleMicroservizi.utenti.domains.dto.requests.CreateComunicazioneAziendaleRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.requests.CreateUtenteRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.requests.UpdateUtenteRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.UtenteResponse;
import com.appAziendaleMicroservizi.utenti.domains.entities.Utente;
import com.appAziendaleMicroservizi.utenti.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.utenti.services.UtenteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/app/v1/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Utente> getById(@PathVariable Long id){
        return new ResponseEntity<>(utenteService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getResponse/{id}")
    public ResponseEntity<UtenteResponse> getByIdWithResponse(@PathVariable Long id){
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
    public ResponseEntity<EntityIdResponse> create(@RequestBody @Valid CreateUtenteRequest request){
        return new ResponseEntity<>(utenteService.createUtente(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateUtenteRequest request){
        return new ResponseEntity<>(utenteService.updateUtente(id, request), HttpStatus.CREATED);
    }

    @PostMapping("/createComunicazioneAziendale")
    public ResponseEntity<EntityIdResponse> createComunicazioneAziendale(@RequestBody @Valid CreateComunicazioneAziendaleRequest request){
        return new ResponseEntity<>(utenteService.createComunicazioneAziendale(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) {
        utenteService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("Utente con id " + id + " eliminato correttamente"), HttpStatus.OK);
    }


}
