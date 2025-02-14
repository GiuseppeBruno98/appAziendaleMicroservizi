package com.appAziendaleMicroservizi.curriculums.controllers;

import com.appAziendaleMicroservizi.curriculums.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.curriculums.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.curriculums.domains.entities.Curriculum;
import com.appAziendaleMicroservizi.curriculums.domains.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.curriculums.services.CurriculumService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/app/v1/curriculums")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Curriculum> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(curriculumService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getByIdUtente/{id}")
    public ResponseEntity<Curriculum> getByIdUtente(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(curriculumService.getByIdUtente(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Curriculum>> getAll() {
        return new ResponseEntity<>(curriculumService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestParam @NotNull Long idUtente, @RequestParam("file") MultipartFile file) throws MyEntityNotFoundException, IOException {
        return new ResponseEntity<>(curriculumService.create(idUtente,file), HttpStatus.CREATED);
    }

    /*@PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateUtenteRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(curriculumService.updateCurriculum(id, request), HttpStatus.CREATED);
    }*/

    @GetMapping("/download/{idUtente}")
    public ResponseEntity<Resource> downloadCurriculum(@PathVariable Long idUtente) {
        return curriculumService.downloadCurriculum(idUtente);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) {
        curriculumService.deleteById(id);
        return new ResponseEntity<>(new GenericResponse("Curriculum con id " + id + " eliminato correttamente"), HttpStatus.OK);
    }


}
