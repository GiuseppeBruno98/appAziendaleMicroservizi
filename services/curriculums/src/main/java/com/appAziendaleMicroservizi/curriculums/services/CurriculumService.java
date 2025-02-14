package com.appAziendaleMicroservizi.curriculums.services;

import com.appAziendaleMicroservizi.curriculums.domains.dto.requests.CreateCurriculumRequest;
import com.appAziendaleMicroservizi.curriculums.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.curriculums.domains.entities.Curriculum;
import com.appAziendaleMicroservizi.curriculums.domains.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.curriculums.mappers.CurriculumMapper;
import com.appAziendaleMicroservizi.curriculums.repositories.CurriculumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private CurriculumMapper curriculumMapper;

    @Autowired
    private UtenteClient utenteClient;

    // Aggiungi il campo per il percorso di upload
    @Value("${file.upload-dir}")
    private String uploadDir;  // Directory dove saranno salvati i file dei curriculum

    public Curriculum getById(Long id) throws MyEntityNotFoundException {
        return curriculumRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("il curriculum con id " + id + " non trovato"));
    }

    public Curriculum getByIdUtente(Long idUtente) {
        return curriculumRepository
                .findByUtenteId(idUtente)
                .orElseThrow(() -> new MyEntityNotFoundException("il curriculum per l'utente con id " + idUtente + " non esiste!"));
    }


    public List<Curriculum> getAll() {
        return curriculumRepository.findAll();
    }


    public EntityIdResponse create(CreateCurriculumRequest request) throws MyEntityNotFoundException {

        var utente = utenteClient.getUtenteResponseById(request.idUtente());

        Curriculum newCurriculum= curriculumRepository.save(curriculumMapper.fromCreateCurriculumRequest(request));
        return new EntityIdResponse(newCurriculum.getId());
    }

    /*
    public EntityIdResponse updateUtente(Long id, UpdateUtenteRequest request) throws MyEntityNotFoundException {
        Utente myUtente = getById(id);
        if (request.nome() != null) myUtente.setNome(request.nome());
        if (request.cognome()!= null) myUtente.setCognome(request.cognome());
        if (request.password() != null) myUtente.setPassword(request.password());
        if (request.luogoNascita() != null) myUtente.setLuogoNascita(request.luogoNascita());
        if (request.telefono() != null) myUtente.setTelefono(request.telefono());
        if (request.imgUtente() != null) myUtente.setImgUtente(request.imgUtente());
        if (request.idPosizioneLavorativa() != null) {
            myUtente.setIdPosizioneLavorativa(posizioneLavorativaService.getById(request.idPosizioneLavorativa()));
        }
        return new EntityIdResponse(utenteRepository.save(myUtente).getId());
    }
    */


    /**
     * Gestisce l'upload del curriculum per un utente.
     */
    public void uploadCurriculum(Long idUtente, MultipartFile file) throws IOException {
        // Crea la directory di upload se non esiste
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Crea un nome unico per il file (per esempio basato sull'ID utente)
        String fileName = idUtente + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Salva il file nella directory
        file.transferTo(filePath);

        // Verifica se esiste già un curriculum per l'utente
        Optional<Curriculum> existingCurriculum = curriculumRepository.findByUtenteId(idUtente);
        if (existingCurriculum.isPresent()) {
            // Se esiste già, aggiorna il percorso del file
            Curriculum existing = existingCurriculum.get();
            existing.setPath(filePath.toString());
            curriculumRepository.save(existing);
        } else {
            // Se non esiste, crea un nuovo curriculum
            Curriculum newCurriculum = new Curriculum();
            newCurriculum.setIdUtente(idUtente);
            newCurriculum.setPath(filePath.toString());
            curriculumRepository.save(newCurriculum);
        }
    }

    /**
     * Restituisce il percorso del curriculum dell'utente per il download.
     */
    public Path getCurriculumFile(Long idUtente) throws MyEntityNotFoundException {
        Curriculum curriculum = getByIdUtente(idUtente);
        return Paths.get(curriculum.getPath());
    }
    public void deleteById(Long id) {
        curriculumRepository.deleteById(id);
    }

}
