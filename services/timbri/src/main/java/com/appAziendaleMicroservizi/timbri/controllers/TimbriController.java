package com.appAziendaleMicroservizi.timbri.controllers;

import com.appAziendaleMicroservizi.timbri.domains.dto.requests.CreateTimbriRequest;
import com.appAziendaleMicroservizi.timbri.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.timbri.domains.dto.responses.TimbriResponse;
import com.appAziendaleMicroservizi.timbri.domains.entities.Timbri;
import com.appAziendaleMicroservizi.timbri.domains.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.timbri.services.TimbriService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/timbri")
public class TimbriController {

    @Autowired
    private TimbriService timbriService;

    @GetMapping("/get/{id}")
    public ResponseEntity<TimbriResponse> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(timbriService.getByIdWithResponse(id), HttpStatus.OK);
    }

    @GetMapping("/getByidUtente/{id}")
    public ResponseEntity<List<TimbriResponse>> getByIdUtente(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(timbriService.getAllResponseByIdUtente(id), HttpStatus.OK);
    }

    @PostMapping("/inizio")
    public ResponseEntity<?> inizio(@RequestBody @Valid CreateTimbriRequest request) throws MyEntityNotFoundException {
        Object result = timbriService.createTimbri(request);
        if (result.getClass() == GenericResponse.class) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Timbri>> getAll() {
        return new ResponseEntity<>(timbriService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) {
        timbriService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("timbro con id " + id + " eliminato correttamente"), HttpStatus.OK);
    }

    @PutMapping("/inizioPausa/{idUtente}")
    public ResponseEntity<?> inizioPausa(@PathVariable Long idUtente) throws MyEntityNotFoundException {
        Object result = timbriService.inizioPausa(idUtente);
        if (result.getClass() == GenericResponse.class) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/finePausa/{idUtente}")
    public ResponseEntity<?> finePausa(@PathVariable Long idUtente) throws MyEntityNotFoundException {
        Object result = timbriService.finePausa(idUtente);
        if (result.getClass() == GenericResponse.class) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/fine/{idUtente}")
    public ResponseEntity<?> fine(@PathVariable Long idUtente) throws MyEntityNotFoundException {
        return new ResponseEntity<>(timbriService.fine(idUtente), HttpStatus.CREATED);
    }


}
