package com.appAziendaleMicroservizi.pubblicazioni.services;


import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CommentoELikeRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CommentoRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.LikeRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.UpdateCommentoELikeRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.CommentoELikeResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.CommentoELike;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.News;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.mappers.CommentoELikeMapper;
import com.appAziendaleMicroservizi.pubblicazioni.repositories.CommentoELikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentoELikeService {

    @Autowired
    CommentoELikeRepository commentoELikeRepository;

    @Autowired
    CommentoELikeMapper commentoELikeMapper;

    @Autowired
    NewsService newsService;

    @Autowired
    private UtenteClient utenteClient;

    public CommentoELike getById(Long id) throws MyEntityNotFoundException {
        return commentoELikeRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("utente con id " + id + " non trovato"));
    }

    public List<CommentoELikeResponse> getResponseByNewsId(Long id) throws MyEntityNotFoundException {
        News news=newsService.getById(id);
        List<CommentoELike> cels=commentoELikeRepository
                .findByNewsId(news);
        List<CommentoELikeResponse> celsResponses = new ArrayList<>();
        for (CommentoELike cel : cels){
            celsResponses.add(commentoELikeMapper.toCommentoELikeResponse(cel));
        }
        return celsResponses;
    }

    public List<CommentoELike> getByNewsId(Long id) throws MyEntityNotFoundException {
        News news=newsService.getById(id);
        return commentoELikeRepository.findByNewsId(news);
    }

    public List<CommentoELikeResponse> getResponseByCreatorId(Long id) throws MyEntityNotFoundException {
        var utente = utenteClient.getUtenteResponseById(id);

        List<CommentoELike> cels=commentoELikeRepository
                .findByCreatorId(id);
        List<CommentoELikeResponse> celsResponses = new ArrayList<>();
        for (CommentoELike cel : cels){
            celsResponses.add(commentoELikeMapper.toCommentoELikeResponse(cel));
        }
        return celsResponses;
    }

    public List<CommentoELike> getByCreatorId(Long id) throws MyEntityNotFoundException {
        var utente = utenteClient.getUtenteResponseById(id);
        return commentoELikeRepository
                .findByCreatorId(id);
    }

    public CommentoELikeResponse getResponseByCreatorIdAndNewsId(Long idCreator,Long idNews) throws MyEntityNotFoundException {
        var utente = utenteClient.getUtenteResponseById(idCreator);
        News news= newsService.getById(idNews);
        CommentoELikeResponse cel=commentoELikeMapper.toCommentoELikeResponse(commentoELikeRepository
                .findByCreatorIdAndNewsId(idCreator,news)
                .orElseThrow(() -> new MyEntityNotFoundException("CommentoELike con creatorId " + idCreator + " e NewsId "+ idNews + " non trovato")));
        return cel;
    }

    public CommentoELike getByCreatorIdAndNewsId(Long idCreator,Long idNews) throws MyEntityNotFoundException {
        var utente = utenteClient.getUtenteResponseById(idCreator);
        News news= newsService.getById(idNews);
        return commentoELikeRepository
                .findByCreatorIdAndNewsId(idCreator,news)
                .orElse(null);
    }

    public CommentoELikeResponse getByIdWithResponse(Long id) throws MyEntityNotFoundException {
        return commentoELikeMapper.toCommentoELikeResponse(commentoELikeRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("l'utente con id " + id + " non esiste!")));
    }

    public List<CommentoELike> getAll() {
        return commentoELikeRepository.findAll();
    }

    public List<CommentoELikeResponse> getAllWithResponse() {
        return commentoELikeRepository.findAll()
                .stream()
                .map(id -> {
                    try {
                        return commentoELikeMapper.toCommentoELikeResponse(id);
                    } catch (MyEntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public EntityIdResponse createCommento(CommentoRequest request) throws MyEntityNotFoundException {
        CommentoELike cel=getByCreatorIdAndNewsId(request.creatorId(),request.newsId());
        if(cel != null){
            throw new IllegalArgumentException("non puoi creare un commento che già c'è");
        }else{
            CommentoELike commentoELikeSaved = commentoELikeRepository.save(commentoELikeMapper.fromCommentoRequest(request));
            return new EntityIdResponse(commentoELikeSaved.getId());
        }
    }
    //da aggiustare
    public EntityIdResponse createLike(LikeRequest request) throws MyEntityNotFoundException {
        CommentoELike cel=getByCreatorIdAndNewsId(request.creatorId(),request.newsId());
        if(cel != null){
            throw new IllegalArgumentException("non puoi creare un like che già c'è");
        }else{
            CommentoELike commentoELikeSaved = commentoELikeRepository.save(commentoELikeMapper.fromLikeRequest(request));
            return new EntityIdResponse(commentoELikeSaved.getId());
        }
    }

    public EntityIdResponse createCommentoELike(CommentoELikeRequest request) throws MyEntityNotFoundException {
        CommentoELike cel=getByCreatorIdAndNewsId(request.creatorId(),request.newsId());
        if(cel != null){
            throw new IllegalArgumentException("non puoi creare un commento/like che già c'è");
        }else{
            CommentoELike commentoELikeSaved = commentoELikeRepository.save(commentoELikeMapper.fromCommentoELikeRequest(request));
            return new EntityIdResponse(commentoELikeSaved.getId());
        }
    }

    public EntityIdResponse updateCommento(Long id, UpdateCommentoELikeRequest request) throws MyEntityNotFoundException {
        CommentoELike myCommentoELike = getById(id);
        if (request.commento() != null) myCommentoELike.setCommento(request.commento());
        return new EntityIdResponse(commentoELikeRepository.save(myCommentoELike).getId());
    }

    public EntityIdResponse updateLike(Long id) throws MyEntityNotFoundException {
        CommentoELike myCommentoELike = getById(id);
        myCommentoELike.setMiPiace(!myCommentoELike.getMiPiace());
        return new EntityIdResponse(commentoELikeRepository.save(myCommentoELike).getId());
    }

    public EntityIdResponse updateCommentoELike(Long id, UpdateCommentoELikeRequest request) throws MyEntityNotFoundException {
        CommentoELike myCommentoELike = getById(id);
        if (request.commento() != null) myCommentoELike.setCommento(request.commento());
        myCommentoELike.setMiPiace(!myCommentoELike.getMiPiace());
        return new EntityIdResponse(commentoELikeRepository.save(myCommentoELike).getId());
    }

    public EntityIdResponse updateCommentoByIdUtenteAndIdNews(Long idCreator,Long idNews, UpdateCommentoELikeRequest request) throws MyEntityNotFoundException {
        CommentoELike myCommentoELike = getByCreatorIdAndNewsId(idCreator,idNews);
        if(myCommentoELike == null){
            throw new IllegalArgumentException("Commento con idCreator "+ idCreator + " e idNews "+ idNews + " non trovato");
        }
        if (request.commento() != null) myCommentoELike.setCommento(request.commento());
        return new EntityIdResponse(commentoELikeRepository.save(myCommentoELike).getId());
    }

    public EntityIdResponse updateLikeByIdUtenteAndIdNews(Long idCreator,Long idNews) throws MyEntityNotFoundException {
        CommentoELike myCommentoELike = getByCreatorIdAndNewsId(idCreator,idNews);
        if(myCommentoELike == null){
            throw new IllegalArgumentException("Commento con idCreator "+ idCreator + " e idNews "+ idNews + " non trovato");
        }
        myCommentoELike.setMiPiace(!myCommentoELike.getMiPiace());
        return new EntityIdResponse(commentoELikeRepository.save(myCommentoELike).getId());
    }

    public EntityIdResponse updateCommentoELikeByIdUtenteAndIdNews(Long idCreator,Long idNews, UpdateCommentoELikeRequest request) throws MyEntityNotFoundException {
        CommentoELike myCommentoELike = getByCreatorIdAndNewsId(idCreator,idNews);
        if(myCommentoELike == null){
            throw new IllegalArgumentException("Commento con idCreator "+ idCreator + " e idNews "+ idNews + " non trovato");
        }
        if (request.commento() != null) myCommentoELike.setCommento(request.commento());
        myCommentoELike.setMiPiace(!myCommentoELike.getMiPiace());
        return new EntityIdResponse(commentoELikeRepository.save(myCommentoELike).getId());
    }

    public void deleteById(Long id) {
        commentoELikeRepository.deleteById(id);
    }
    public void deleteByIdUtenteAndIdNews(Long idCreator, Long idNews) throws MyEntityNotFoundException {
        CommentoELike myCommentoELike= getByCreatorIdAndNewsId(idCreator,idNews);
        if (myCommentoELike==null) throw new IllegalArgumentException("Non esiste CommentoELike con idCreator " + idCreator + " e NewsId " + idNews);
        commentoELikeRepository.deleteById(myCommentoELike.getId());
    }
}
