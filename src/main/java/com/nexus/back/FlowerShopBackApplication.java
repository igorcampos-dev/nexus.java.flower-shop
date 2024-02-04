package com.nexus.back;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nexus")
@OpenAPIDefinition(info = @Info(title = "API de floricultura online", version = "1.5.0", description = "Uma api de salvar imagens de flores e envia-las por email."))
public class FlowerShopBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerShopBackApplication.class, args);
	}

}













