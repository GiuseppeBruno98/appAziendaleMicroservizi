package com.appAziendaleMicroservizi.timbri.services;

import com.appAziendaleMicroservizi.timbri.domains.entities.Timbri;
import com.appAziendaleMicroservizi.timbri.repositories.TimbriRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class TimbriDailyJob implements Job {
    @Autowired
    TimbriRepository timbriRepository;

    @Autowired
    UtenteClient utenteClient;

    @Value("${file.upload-daily-dir-timbri}")
    private String uploadDir;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Logica del job
        System.out.println("Esecuzione del job giornaliero alle " + LocalTime.now());
        List<Timbri> timbri= timbriRepository.findAll()
                .stream()
                .filter(timbro -> timbro.getOraInizio().toLocalDate().equals(LocalDate.now()))
                .toList();

        // Crea un nuovo workbook
        Workbook workbook = new XSSFWorkbook();
        // Crea un foglio di lavoro
        Sheet sheet = workbook.createSheet("Timbri");

        // Crea la riga di intestazione
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("ID Utente");
        headerRow.createCell(2).setCellValue("Nome");
        headerRow.createCell(3).setCellValue("Cognome");
        headerRow.createCell(4).setCellValue("Ora inizio");
        headerRow.createCell(5).setCellValue("Ora inizio pausa");
        headerRow.createCell(6).setCellValue("Ora fine pausa");
        headerRow.createCell(7).setCellValue("Ora fine");

        // Popola il foglio con i dati della lista
        int rowIdx = 1;
        for (Timbri t : timbri) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(t.getId());
            var utente = utenteClient.getUtenteResponseById(t.getUtenteId());
            row.createCell(1).setCellValue(utente.id());
            row.createCell(2).setCellValue(utente.nome());
            row.createCell(3).setCellValue(utente.cognome());
            row.createCell(4).setCellValue(t.getOraInizio().toString());
            if(t.getInizioPausa()!= null){
                row.createCell(5).setCellValue(t.getInizioPausa().toString());
            }else{
                row.createCell(5).setCellValue("null");
            }
            if(t.getFinePausa()!= null){
                row.createCell(6).setCellValue(t.getFinePausa().toString());
            }else{
                row.createCell(6).setCellValue("null");
            }
            if(t.getOraFine()!= null){
                row.createCell(7).setCellValue(t.getOraFine().toString());
            }else{
                row.createCell(7).setCellValue("null");
            }


        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("errore nella creazione della directory per i timbri");
            }
        }

        // Crea un nome unico per il file (per esempio basato sull'ID utente)
        String fileName = "timbri-"+ LocalDateTime.now().toLocalDate() +".xlsx";
        Path filePath = uploadPath.resolve(fileName);

        // Scrive il workbook in un file
        try (FileOutputStream fileOut = new FileOutputStream(String.valueOf(filePath))) {
            try {
                workbook.write(fileOut);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(createEmail("crinq98@gmail.com"));



    }

    private SimpleMailMessage createEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // a chi mando la mail
        message.setReplyTo("crinq98@gmail.com"); // a chi rispondo se faccio "rispondi"
        message.setFrom("crinq98@gmail.com"); // da chi viene la mail
        message.setSubject("Email Timbri"); // il TITOLO!
        message.setText("Ciao!" ); // il testo!
        return message; // ritorno il messaggio
    }

}