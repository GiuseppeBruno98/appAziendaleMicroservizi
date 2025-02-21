package com.appAziendaleMicroservizi.api_gateway.repositories;


import com.appAziendaleMicroservizi.api_gateway.domains.entities.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList,Long> {

    Optional<TokenBlackList> getByToken(String token);

}
