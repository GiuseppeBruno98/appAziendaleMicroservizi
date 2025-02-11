package com.appAziendaleMicroservizi.timbri.repositories;


import com.appAziendaleMicroservizi.timbri.domains.entities.Timbri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimbriRepository extends JpaRepository<Timbri, Long>{

    List<Timbri> findByUtenteId(Long utenteId);
}
