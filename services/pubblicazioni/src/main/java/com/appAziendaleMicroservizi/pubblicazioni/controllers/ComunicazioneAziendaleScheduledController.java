package com.appAziendaleMicroservizi.pubblicazioni.controllers;

import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateComunicazioneAziendaleScheduledRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.UpdateComunicazioneAziendaleRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.ComunicazioneAziendaleScheduledResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.ComunicazioneAziendaleScheduled;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.services.ComunicazioneAziendaleScheduledService;
import jakarta.validation.Valid;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/comunicazioneAziendaleScheduled")
public class ComunicazioneAziendaleScheduledController {

    @Autowired
    private ComunicazioneAziendaleScheduledService comunicazioneAziendaleScheduledService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ComunicazioneAziendaleScheduled> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(comunicazioneAziendaleScheduledService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getResponse/{id}")
    public ResponseEntity<ComunicazioneAziendaleScheduledResponse> getByIdWithResponse(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(comunicazioneAziendaleScheduledService.getByIdWithResponse(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ComunicazioneAziendaleScheduled>> getAll() {
        return new ResponseEntity<>(comunicazioneAziendaleScheduledService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/allResponse")
    public ResponseEntity<List<ComunicazioneAziendaleScheduledResponse>> getAllWithResponse() {
        return new ResponseEntity<>(comunicazioneAziendaleScheduledService.getAllWithResponse(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestBody @Valid CreateComunicazioneAziendaleScheduledRequest request) throws MyEntityNotFoundException, SchedulerException {
        return new ResponseEntity<>(comunicazioneAziendaleScheduledService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateComunicazioneAziendaleRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(comunicazioneAziendaleScheduledService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) throws MyEntityNotFoundException {
        comunicazioneAziendaleScheduledService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("ComunicazioneAziendale con id " + id + " eliminata correttamente"), HttpStatus.OK);
    }
}