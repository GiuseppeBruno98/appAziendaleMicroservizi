package com.appAziendaleMicroservizi.curriculums.repositories;


import com.appAziendaleMicroservizi.curriculums.domains.entities.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long>{

    Optional<Curriculum> findByIdUtente(Long idUtente);

}
