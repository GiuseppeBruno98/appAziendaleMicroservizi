package com.appAziendaleMicroservizi.utenti.controllers;


import com.appAziendaleMicroservizi.utenti.domains.dto.requests.DipartimentoRequest;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.utenti.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.utenti.domains.entities.Dipartimento;
import com.appAziendaleMicroservizi.utenti.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.utenti.services.DipartimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/dipartimento")
public class DipartimentoController {

    @Autowired
    DipartimentoService dipartimentoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Dipartimento> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(dipartimentoService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dipartimento>> getAll() {
        return new ResponseEntity<>(dipartimentoService.getAll(), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestBody @Valid DipartimentoRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(dipartimentoService.createDipartimento(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid DipartimentoRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(dipartimentoService.updateDipartimento(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) {
        dipartimentoService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("Dipartimento con id " + id + " eliminato correttamente"), HttpStatus.OK);
    }

}
