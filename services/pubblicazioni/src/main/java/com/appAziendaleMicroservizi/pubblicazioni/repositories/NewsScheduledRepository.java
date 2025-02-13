package com.appAziendaleMicroservizi.pubblicazioni.repositories;


import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.NewsScheduled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsScheduledRepository extends JpaRepository<NewsScheduled, Long>{
}
