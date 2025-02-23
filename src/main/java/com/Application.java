package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new io.swagger.v3.oas.models.info.Info().title("Inventory Management API").version("1.0")
						.description("API documentation for the Inventory Management system")
						.contact(new Contact().name("Tejas Shah").email("tejas@example.com")))
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("Bearer Authentication",
						new SecurityScheme().name("Bearer Authentication").type(SecurityScheme.Type.HTTP)
								.scheme("bearer").bearerFormat("JWT")));
	}

}
