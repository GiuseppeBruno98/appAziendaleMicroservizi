package com.appAziendaleMicroservizi.utente.repositories;


import com.appAziendaleMicroservizi.utente.domains.entities.PosizioneLavorativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosizioneLavorativaRepository extends JpaRepository<PosizioneLavorativa, Long>{
}
