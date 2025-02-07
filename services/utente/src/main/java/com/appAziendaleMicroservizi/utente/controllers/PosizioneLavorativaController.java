package com.appAziendaleMicroservizi.utente.controllers;



import com.appAziendaleMicroservizi.utente.domains.dto.requests.PosizioneLavorativaRequest;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.utente.domains.dto.responses.PosizioneLavorativaResponse;
import com.appAziendaleMicroservizi.utente.domains.entities.PosizioneLavorativa;
import com.appAziendaleMicroservizi.utente.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.utente.services.PosizioneLavorativaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/posizioneLavorativa")
public class PosizioneLavorativaController {

    @Autowired
    private PosizioneLavorativaService posizioneLavorativaService;

    @GetMapping("/get/{id}")
    public ResponseEntity<PosizioneLavorativa> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(posizioneLavorativaService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getresponse/{id}")
    public ResponseEntity<PosizioneLavorativaResponse> getByIdWithResponse(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(posizioneLavorativaService.getByIdWithResponse(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PosizioneLavorativa>> getAll() {
        return new ResponseEntity<>(posizioneLavorativaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/allResponse")
    public ResponseEntity<List<PosizioneLavorativaResponse>> getAllResponse() {
        return new ResponseEntity<>(posizioneLavorativaService.getAllWithResponse(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestBody @Valid PosizioneLavorativaRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(posizioneLavorativaService.createPosizioneLavorativa(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid PosizioneLavorativaRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(posizioneLavorativaService.updatePosizioneLavorativa(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) {
        posizioneLavorativaService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("PosizioneLavorativa con id " + id + " eliminato correttamente"), HttpStatus.OK);
    }
}
