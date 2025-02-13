package com.appAziendaleMicroservizi.pubblicazioni.controllers;

import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.*;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.CommentoELikeResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.GenericResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.CommentoELike;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.services.CommentoELikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/commentoELike")
public class CommentoELikeController {

    @Autowired
    private CommentoELikeService commentoELikeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<CommentoELike> getById(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getResponse/{id}")
    public ResponseEntity<CommentoELikeResponse> getByIdWithResponse(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.getByIdWithResponse(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentoELike>> getAll() {
        return new ResponseEntity<>(commentoELikeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/allResponse")
    public ResponseEntity<List<CommentoELikeResponse>> getAllwithResponse() {
        return new ResponseEntity<>(commentoELikeService.getAllWithResponse(), HttpStatus.OK);
    }

    @PostMapping("/createCommento")
    public ResponseEntity<EntityIdResponse> createCommento(@RequestBody @Valid CommentoRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.createCommento(request), HttpStatus.CREATED);
    }
    @PostMapping("/createLike")
    public ResponseEntity<EntityIdResponse> createLike(@RequestBody @Valid LikeRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.createLike(request), HttpStatus.CREATED);
    }
    @PostMapping("/createCommentoELike")
    public ResponseEntity<EntityIdResponse> createCommentoELike(@RequestBody @Valid CommentoELikeRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.createCommentoELike(request), HttpStatus.CREATED);
    }

    @PutMapping("/updateCommentoAdmin/{id}")
    public ResponseEntity<EntityIdResponse> updateCommento(@PathVariable Long id, @RequestBody @Valid UpdateCommentoELikeRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.updateCommento(id, request), HttpStatus.OK);
    }
    @PutMapping("/updateLikeAdmin/{id}")
    public ResponseEntity<EntityIdResponse> updateLike(@PathVariable Long id) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.updateLike(id), HttpStatus.OK);
    }
    @PutMapping("/updateCommentoELikeAdmin/{id}")
    public ResponseEntity<EntityIdResponse> updateCommentoELike(@PathVariable Long id, @RequestBody @Valid UpdateCommentoELikeRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.updateCommentoELike(id, request), HttpStatus.OK);
    }

    @PutMapping("/updateCommento")
    public ResponseEntity<EntityIdResponse> updateCommentoByIdUtenteAndIdNews(@RequestBody @Valid UpdateCommentoELikeByIdUtenteAndIdNewsRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.updateCommentoByIdUtenteAndIdNews(request.idCreator(),request.idNews(), request.req()), HttpStatus.OK);
    }
    @PutMapping("/updateLike")
    public ResponseEntity<EntityIdResponse> updateLikeByIdUtenteAndIdNews(@RequestBody @Valid UtenteAndNewsRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.updateLikeByIdUtenteAndIdNews(request.idCreator(),request.idNews()), HttpStatus.OK);
    }
    @PutMapping("/updateCommentoELike")
    public ResponseEntity<EntityIdResponse> updateCommentoELikeByIdUtenteAndIdNews(@RequestBody @Valid UpdateCommentoELikeByIdUtenteAndIdNewsRequest request) throws MyEntityNotFoundException {
        return new ResponseEntity<>(commentoELikeService.updateCommentoELikeByIdUtenteAndIdNews(request.idCreator(),request.idNews(), request.req()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<GenericResponse> deleteById(@PathVariable Long id) throws MyEntityNotFoundException  {
        commentoELikeService.deleteById(id);
        return new ResponseEntity<>(
                new GenericResponse("CommentoELike con id " + id + " eliminata correttamente"), HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse> deleteByIdUtenteAndIdNews(@RequestBody @Valid UtenteAndNewsRequest request) throws MyEntityNotFoundException  {
        commentoELikeService.deleteByIdUtenteAndIdNews(request.idCreator(),request.idNews());
        return new ResponseEntity<>(
                new GenericResponse("CommentoELike con idCreator " + request.idCreator() + " e NewsId " + request.idNews() + " eliminata correttamente"), HttpStatus.OK);
    }

}
