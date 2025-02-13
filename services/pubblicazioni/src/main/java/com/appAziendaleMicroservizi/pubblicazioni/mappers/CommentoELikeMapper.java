package com.appAziendaleMicroservizi.pubblicazioni.mappers;


import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CommentoELikeRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CommentoRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.LikeRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.CommentoELikeResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.CommentoELike;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentoELikeMapper {

    @Autowired
    NewsService newsService;

    public CommentoELike fromCommentoRequest(CommentoRequest request) throws MyEntityNotFoundException {
        return CommentoELike
                .builder()
                .commento(request.commento())
                .miPiace(false)
                .creatorId(request.creatorId())
                .newsId(newsService.getById(request.newsId()))
                .build();
    }

    public CommentoELike fromLikeRequest(LikeRequest request) throws MyEntityNotFoundException {
        return CommentoELike
                .builder()
                .miPiace(true)
                .creatorId(request.creatorId())
                .newsId(newsService.getById(request.newsId()))
                .build();
    }

    public CommentoELike fromCommentoELikeRequest(CommentoELikeRequest request) throws MyEntityNotFoundException {
        return CommentoELike
                .builder()
                .miPiace(true)
                .commento(request.commento())
                .creatorId(request.creatorId())
                .newsId(newsService.getById(request.newsId()))
                .build();
    }

    public CommentoELikeResponse toCommentoELikeResponse(CommentoELike request) throws MyEntityNotFoundException {

        return CommentoELikeResponse
                .builder()
                .id(request.getId())
                .commento(request.getCommento())
                .miPiace(request.getMiPiace())
                .newsId(request.getNewsId().getId())
                .userId(request.getCreatorId())
                .build();
    }
}
