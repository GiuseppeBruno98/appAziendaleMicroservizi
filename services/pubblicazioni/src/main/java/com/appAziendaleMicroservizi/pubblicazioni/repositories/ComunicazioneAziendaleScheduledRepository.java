package com.appAziendaleMicroservizi.pubblicazioni.repositories;


import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.ComunicazioneAziendaleScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunicazioneAziendaleScheduledRepository extends JpaRepository<ComunicazioneAziendaleScheduled, Long>{
}
