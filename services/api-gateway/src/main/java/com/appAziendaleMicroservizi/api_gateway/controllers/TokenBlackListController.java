package com.appAziendaleMicroservizi.api_gateway.controllers;

import com.appAziendaleMicroservizi.api_gateway.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.api_gateway.services.TokenBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/v1/tokenBlackList")
public class TokenBlackListController {

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @PostMapping("/insert")
    public ResponseEntity<EntityIdResponse> getById(@RequestParam Long idUtente, String token){
        return new ResponseEntity<>(tokenBlackListService.insertToken(idUtente, token), HttpStatus.OK);
    }

}