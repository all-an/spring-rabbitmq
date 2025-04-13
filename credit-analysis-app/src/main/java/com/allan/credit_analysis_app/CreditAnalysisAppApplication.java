package com.allan.credit_analysis_app;

import com.allan.credit_analysis_app.service.CreditAnalysisService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@SpringBootApplication
public class CreditAnalysisAppApplication {

	private CreditAnalysisService creditAnalysisService;

	public static void main(String[] args) {
		SpringApplication.run(CreditAnalysisAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			creditAnalysisService.analyze(null);
		};
	}

}
