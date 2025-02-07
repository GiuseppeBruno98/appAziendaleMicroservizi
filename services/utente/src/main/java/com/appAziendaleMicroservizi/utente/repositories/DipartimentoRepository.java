package com.appAziendaleMicroservizi.utente.repositories;

import com.appAziendaleMicroservizi.utente.domains.entities.Dipartimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipartimentoRepository extends JpaRepository<Dipartimento, Long>{
}
