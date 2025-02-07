package com.appAziendaleMicroservizi.pubblicazioni.repositories;

import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.ComunicazioneAziendale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunicazioneAziendaleRepository extends JpaRepository<ComunicazioneAziendale, Long>{
}
