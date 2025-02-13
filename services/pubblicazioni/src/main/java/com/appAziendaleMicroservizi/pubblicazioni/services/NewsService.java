package com.appAziendaleMicroservizi.pubblicazioni.services;


import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateNewsRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.UpdateNewsRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.NewsResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.News;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.mappers.NewsMapper;
import com.appAziendaleMicroservizi.pubblicazioni.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UtenteClient utenteClient;

    @Autowired
    private PosizioneLavorativaClient posizioneLavorativaClient;

    public News getById(Long id) throws MyEntityNotFoundException {
        return newsRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("News con id " + id + " non trovata"));
    }

    public NewsResponse getByIdWithResponse(Long id) throws MyEntityNotFoundException {
        return newsMapper.toNewsResponse(newsRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("News con id " + id + " non esiste!")));
    }

    public List<News> getAll() {
        return newsRepository.findAll();
    }

    public List<NewsResponse> getAllWithResponse()  {
        return newsRepository.findAll()
                .stream()
                .map(id -> {
                    try {
                        return newsMapper.toNewsResponse(id);
                    } catch (MyEntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }


    public EntityIdResponse createNews(CreateNewsRequest request) throws MyEntityNotFoundException, IllegalAccessException {
        var utente = utenteClient.getUtenteResponseById(request.creatorId());
        var posizioneLavorativa = posizioneLavorativaClient.getPosizioneLavorativaResponseById(utente.idPosizioneLavorativa());
        if(!posizioneLavorativa.nome().equals("publisher")){
            throw new IllegalAccessException("Solo i publisher possono creare le news");
        }
        News savedNews = newsRepository.save(newsMapper.fromCreateNewsRequest(request));
        return new EntityIdResponse(savedNews.getId());
    }

    public EntityIdResponse updateNews(Long id, UpdateNewsRequest request) throws MyEntityNotFoundException {
        News news = getById(id);
        if (request.titolo() != null) news.setTitolo(request.titolo());
        if (request.contenuto() != null) news.setContenuto(request.contenuto());
        if (request.immagine() != null) news.setImmagine(request.immagine());
        return new EntityIdResponse(newsRepository.save(news).getId());
    }

    public void deleteById(Long id) throws MyEntityNotFoundException {
        if (!newsRepository.existsById(id)) {
            throw new MyEntityNotFoundException("News con id " + id + " non trovata");
        }
        newsRepository.deleteById(id);
    }
}