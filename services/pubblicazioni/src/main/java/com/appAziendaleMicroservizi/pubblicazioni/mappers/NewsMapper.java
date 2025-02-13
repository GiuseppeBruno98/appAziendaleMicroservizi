package com.appAziendaleMicroservizi.pubblicazioni.mappers;


import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateNewsRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateNewsScheduledRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.NewsResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.NewsScheduledResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.News;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.NewsScheduled;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NewsMapper {


    public News fromCreateNewsRequest(CreateNewsRequest request) throws MyEntityNotFoundException {
        return News.builder()
                .titolo(request.titolo())
                .contenuto(request.contenuto())
                .immagine(request.immagine())
                .creatorId(request.creatorId())
                .build();
    }

    public NewsScheduled fromCreateNewsScheduledRequestToNewsScheduled(CreateNewsScheduledRequest request) throws MyEntityNotFoundException {
        return NewsScheduled.builder()
                .titolo(request.titolo())
                .contenuto(request.contenuto())
                .immagine(request.immagine())
                .publishTime(request.publishTime())
                .creatorId(request.creatorId())
                .build();
    }

    public CreateNewsRequest fromCreateNewsScheduledRequestToCreateNewsRequest(CreateNewsScheduledRequest request) throws MyEntityNotFoundException {
        return CreateNewsRequest.builder()
                .titolo(request.titolo())
                .contenuto(request.contenuto())
                .immagine(request.immagine())
                .creatorId(request.creatorId())
                .build();
    }

    public NewsResponse toNewsResponse(News request) throws MyEntityNotFoundException  {
        return NewsResponse.builder()
                .id(request.getId())
                .titolo(request.getTitolo())
                .contenuto(request.getContenuto())
                .immagine(request.getImmagine())
                .creatorId(request.getCreatorId())
                .build();
    }

    public NewsScheduledResponse toNewsScheduledResponse(NewsScheduled request) throws MyEntityNotFoundException  {
        return NewsScheduledResponse.builder()
                .id(request.getId())
                .titolo(request.getTitolo())
                .contenuto(request.getContenuto())
                .immagine(request.getImmagine())
                .publishTime(request.getPublishTime())
                .creatorId(request.getCreatorId())
                .build();
    }
}