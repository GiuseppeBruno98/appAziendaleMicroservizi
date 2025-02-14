package com.appAziendaleMicroservizi.curriculums.services;

import com.appAziendaleMicroservizi.curriculums.domains.dto.responses.EntityIdResponse;
import com.appAziendaleMicroservizi.curriculums.domains.entities.Curriculum;
import com.appAziendaleMicroservizi.curriculums.domains.exceptions.MyEntityNotFoundException;
import com.appAziendaleMicroservizi.curriculums.repositories.CurriculumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private UtenteClient utenteClient;

    // Aggiungi il campo per il percorso di upload
    @Value("${file.upload-dir-curriculum}")
    private String uploadDir;  // Directory dove saranno salvati i file dei curriculum

    public Curriculum getById(Long id) throws MyEntityNotFoundException {
        return curriculumRepository
                .findById(id)
                .orElseThrow(() -> new MyEntityNotFoundException("il curriculum con id " + id + " non trovato"));
    }

    public Curriculum getByIdUtente(Long idUtente) {
        return curriculumRepository
                .findByIdUtente(idUtente)
                .orElseThrow(() -> new MyEntityNotFoundException("il curriculum per l'utente con id " + idUtente + " non esiste!"));
    }


    public List<Curriculum> getAll() {
        return curriculumRepository.findAll();
    }


    public EntityIdResponse upload(Long idUtente, MultipartFile file) throws MyEntityNotFoundException, IOException {
        var utente = utenteClient.getUtenteResponseById(idUtente);
        Curriculum newCurriculum= uploadCurriculum(idUtente, file);

        return new EntityIdResponse(newCurriculum.getId());
    }

    /**
     * Gestisce l'upload del curriculum per un utente.
     */
    public Curriculum uploadCurriculum(Long idUtente, MultipartFile file) throws IOException {
        // Crea la directory di upload se non esiste
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Crea un nome unico per il file (per esempio basato sull'ID utente)
        String fileName = idUtente + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        String fileExtension = getFileExtension(fileName);
        // Verifica che il file sia .pdf o .docx
        if (!(fileExtension.equals("pdf") || fileExtension.equals("docx") || fileExtension.equals("doc"))) {
            throw new IllegalArgumentException("Il file deve essere un PDF o un documento Word (.docx o .doc).");
        }

        // Salva il file nella directory
        file.transferTo(filePath);

        // Verifica se esiste già un curriculum per l'utente
        Curriculum existingCurriculum = curriculumRepository.findByIdUtente(idUtente).orElse(null);;
        if (existingCurriculum != null) {
            // Se esiste già, aggiorna il percorso del file
            Curriculum existing = existingCurriculum;
            existing.setPath(filePath.toString());
            return curriculumRepository.save(existing);
        } else {
            // Se non esiste, crea un nuovo curriculum
            Curriculum newCurriculum = new Curriculum();
            newCurriculum.setIdUtente(idUtente);
            newCurriculum.setPath(filePath.toString());
            return curriculumRepository.save(newCurriculum);
        }
    }

    // Metodo che restituisce una risorsa (file) con le intestazioni appropriate
    public ResponseEntity<Resource> download(Long idUtente) {
        // Ottieni il curriculum per l'utente
        Curriculum curriculum = curriculumRepository.findByIdUtente(idUtente).orElse(null);

        //Se non esiste il curriculum, restituisci null
        if (curriculum == null) {
            throw new IllegalArgumentException("Non ci sono curriculum con l'idUtente " + idUtente);
        }

        // Ottieni il percorso del file dal curriculum
        String filePath = curriculum.getPath();

        // Crea una risorsa dal file nel sistema di file
        Resource resource = new FileSystemResource(filePath);

        // Verifica che il file esista
        if (!resource.exists()) {
            throw new IllegalArgumentException("il file " + filePath + " non è presente nell'archivio");  // Se il file non esiste, restituisci null
        }
        String fileExtension = getFileExtension(filePath);

        // Imposta le intestazioni necessarie per il download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        if (fileExtension.equals("pdf")) {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);  // Tipo per PDF
        } else if (fileExtension.equals("docx")) {
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");  // Tipo per DOCX
        } else {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);  // Tipo generico per altri formati
        }

        // Restituisci una risposta con la risorsa e le intestazioni
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource); // Restituisci la risorsa per il controller
    }

    // Metodo per ottenere l'estensione del file
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    public void deleteById(Long id) {
        curriculumRepository.deleteById(id);
    }

    public void deleteByIdUtente(Long idUtente) {
        var utente = utenteClient.getUtenteResponseById(idUtente);
        Curriculum curriculum= curriculumRepository.findByIdUtente(idUtente).orElse(null);
        if (curriculum != null) {
            curriculumRepository.deleteById(curriculum.getId());
        } else {
            throw new IllegalArgumentException("il curriculum con idUtente " + idUtente + "non esiste");
        }
    }

}
