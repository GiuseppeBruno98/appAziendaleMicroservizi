package com.appAziendaleMicroservizi.pubblicazioni.controllers;


import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateNewsScheduledRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.UpdateNewsRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.NewsScheduledResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.NewsScheduled;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.services.NewsScheduledService;
import jakarta.validation.Valid;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/newsScheduled")
public class NewsScheduledController {

    @Autowired
    private NewsScheduledService newsScheduledService;

    @GetMapping("/get/{id}")
    public ResponseEntity<NewsScheduled> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(newsScheduledService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getResponse/{id}")
    public ResponseEntity<NewsScheduledResponse> getByIdWithResponse(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(newsScheduledService.getByIdWithResponse(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsScheduled>> getAll() {
        return new ResponseEntity<>(newsScheduledService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/allResponse")
    public ResponseEntity<List<NewsScheduledResponse>> getAllWithResponse() {
        return new ResponseEntity<>(newsScheduledService.getAllWithResponse(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EntityIdResponse> create(@RequestBody @Valid CreateNewsScheduledRequest request) throws MyEntityNotFoundException, SchedulerException {
        return new ResponseEntity<>(newsScheduledService.createNewsScheduled(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityIdResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateNewsRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(newsScheduledService.updateNewsScheduled(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) throws MyEntityNotFoundException {
        newsScheduledService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("News con id " + id + " eliminata correttamente"), HttpStatus.OK);
    }
}