package com.appAziendaleMicroservizi.pubblicazioni.repositories;

import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.CommentoELike;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentoELikeRepository extends JpaRepository<CommentoELike, Long>{

    List<CommentoELike> findByNewsId(News newsId);
    List<CommentoELike> findByCreatorId(Long utenteId);
    Optional<CommentoELike> findByCreatorIdAndNewsId(Long utenteId, News newsId);

}
