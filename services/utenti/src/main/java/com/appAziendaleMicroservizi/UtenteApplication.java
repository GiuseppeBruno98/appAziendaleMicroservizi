package com.appAziendaleMicroservizi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class UtenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtenteApplication.class, args);
	}

}
