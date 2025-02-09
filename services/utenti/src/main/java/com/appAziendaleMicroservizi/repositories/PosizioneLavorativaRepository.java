package com.appAziendaleMicroservizi.repositories;


import com.appAziendaleMicroservizi.domains.entities.PosizioneLavorativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosizioneLavorativaRepository extends JpaRepository<PosizioneLavorativa, Long>{
}
