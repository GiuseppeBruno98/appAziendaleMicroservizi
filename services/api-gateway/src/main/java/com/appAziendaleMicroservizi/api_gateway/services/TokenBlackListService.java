package com.appAziendaleMicroservizi.api_gateway.services;


import com.appAziendaleMicroservizi.api_gateway.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.api_gateway.domains.entities.TokenBlackList;
import com.appAziendaleMicroservizi.api_gateway.repositories.TokenBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlackListService {

    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;


    public Boolean isPresentToken(String token) {
        return tokenBlackListRepository.getByToken(token).isPresent();
    }

    public EntityIdResponse insertToken(Long id_utente, String token) {
        TokenBlackList tokenBlackList = TokenBlackList
                .builder()
                .token(token)
                .idUtente(id_utente)
                .build();
        return new EntityIdResponse(tokenBlackListRepository.save(tokenBlackList).getId());
    }

}
