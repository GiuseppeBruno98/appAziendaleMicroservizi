package com.appAziendaleMicroservizi.utenti.repositories;


import com.appAziendaleMicroservizi.utenti.domains.entities.PosizioneLavorativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosizioneLavorativaRepository extends JpaRepository<PosizioneLavorativa, Long>{
}
