package com.estudo.mentoria;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Mentoria OpenApi", version = "1", description = "API para o projeto mentoria"))
public class MentoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentoriaApplication.class, args);
	}

}
