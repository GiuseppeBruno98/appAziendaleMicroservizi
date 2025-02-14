package com.appAziendaleMicroservizi.pubblicazioni.services;


import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateNewsRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.CreateNewsScheduledRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.requests.UpdateNewsRequest;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.dto.responses.NewsScheduledResponse;
import com.appAziendaleMicroservizi.pubblicazioni.domains.entities.NewsScheduled;
import com.appAziendaleMicroservizi.pubblicazioni.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.pubblicazioni.mappers.NewsMapper;
import com.appAziendaleMicroservizi.pubblicazioni.repositories.NewsScheduledRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class NewsScheduledService implements Job {

    @Autowired
    private NewsScheduledRepository newsScheduledRepository;
    @Autowired
    private NewsService newsService;

    @Autowired
    private UtenteClient utenteClient;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private Scheduler scheduler;

    public NewsScheduled getById(Long id) throws MyEntityNotFoundException {
        return newsScheduledRepository.findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("News con id " + id + " non trovata"));
    }

    public NewsScheduledResponse getByIdWithResponse(Long id) throws MyEntityNotFoundException {
        return newsMapper.toNewsScheduledResponse(newsScheduledRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("NewsScheduled con id " + id + " non esiste!")));
    }

    public List<NewsScheduled> getAll() {
        return newsScheduledRepository.findAll();
    }

    public List<NewsScheduledResponse> getAllWithResponse()  {
        return newsScheduledRepository.findAll()
                .stream()
                .map(id -> {
                    try {
                        return newsMapper.toNewsScheduledResponse(id);
                    } catch (MyEntityNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }


    public EntityIdResponse createNewsScheduled(CreateNewsScheduledRequest request) throws MyEntityNotFoundException, SchedulerException {
        NewsScheduled newsScheduled =newsMapper.fromCreateNewsScheduledRequestToNewsScheduled(request);
        newsScheduledRepository.save(newsScheduled);
        CreateNewsRequest newsRequest= newsMapper.fromCreateNewsScheduledRequestToCreateNewsRequest(request);
        // crea il job per lo schedule della transazione
        JobDetail jobDetail = buildJobDetail(newsScheduled, newsRequest);
        Trigger trigger = buildJobTrigger(jobDetail, Date.from(newsScheduled.getPublishTime().atZone(ZoneId.systemDefault()).toInstant()));
        scheduler.scheduleJob(jobDetail, trigger);
        return EntityIdResponse.builder().id(newsScheduled.getId()).build();
    }

    public EntityIdResponse updateNewsScheduled(Long id, UpdateNewsRequest request) throws MyEntityNotFoundException {
        NewsScheduled news = getById(id);
        if (request.titolo() != null) news.setTitolo(request.titolo());
        if (request.contenuto() != null) news.setContenuto(request.contenuto());
        if (request.immagine() != null) news.setImmagine(request.immagine());
        return new EntityIdResponse(newsScheduledRepository.save(news).getId());
    }

    public void deleteById(Long id) throws MyEntityNotFoundException {
        if (!newsScheduledRepository.existsById(id)) {
            throw new MyEntityNotFoundException("News con id " + id + " non trovata");
        }
        newsScheduledRepository.deleteById(id);
    }
    private Trigger buildJobTrigger(JobDetail jobDetail, Date publishTime) {

        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .startAt(publishTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();

    }

    private JobDetail buildJobDetail(NewsScheduled newsScheduled,
                                     CreateNewsRequest newsRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("entityData", newsRequest); // ---> l'entità che passerò all'execute
        jobDataMap.put("id", newsScheduled.getId()); // ---> l'id del job
        return JobBuilder
                .newJob(NewsScheduledService.class)
                .withIdentity(String.valueOf(newsScheduled.getId()), "newsScheduled")
                .storeDurably()
                .setJobData(jobDataMap)
                .build();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        CreateNewsRequest request = (CreateNewsRequest) jobDataMap.get("entityData");
        Long id_scheduled = jobDataMap.getLongValue("id");
        try {
            newsService.create(request);
        } catch (MyEntityNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        newsScheduledRepository.deleteById(id_scheduled);
    }
}