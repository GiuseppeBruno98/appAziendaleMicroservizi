package com.appAziendaleMicroservizi.utente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UtenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtenteApplication.class, args);
	}

}
