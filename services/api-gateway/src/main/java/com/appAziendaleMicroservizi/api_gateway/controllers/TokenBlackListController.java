package com.appAziendaleMicroservizi.api_gateway.controllers;

import com.appAziendaleMicroservizi.api_gateway.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.api_gateway.domains.entities.TokenBlackList;
import com.appAziendaleMicroservizi.api_gateway.services.TokenBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/tokenBlackList")
public class TokenBlackListController {

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @PostMapping("/insert")
    public ResponseEntity<EntityIdResponse> insertToken(@RequestParam Long idUtente,@RequestParam String token){
        return new ResponseEntity<>(tokenBlackListService.insertToken(idUtente, token), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TokenBlackList>> getAll(){
        return new ResponseEntity<>(tokenBlackListService.getAll(), HttpStatus.OK);
    }

}