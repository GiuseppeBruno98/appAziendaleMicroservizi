package com.appAziendaleMicroservizi.utenti.repositories;

import com.appAziendaleMicroservizi.utenti.domains.entities.Dipartimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipartimentoRepository extends JpaRepository<Dipartimento, Long>{
}
