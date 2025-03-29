package com.allan.proposal_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.NumberFormat;
import java.util.Locale;

@SpringBootApplication
public class ProposalAppApplication {

	@Autowired
	private Locale locale;

	@Autowired
	private String something;

	public static void main(String[] args) {
		SpringApplication.run(ProposalAppApplication.class, args);
	}

	/**
	 * For learning purpose
	 * @return Locale value accordingly to a locale specified
	 */
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			double someValue = Double.parseDouble("10.50");
			System.out.println(NumberFormat.getCurrencyInstance(locale).format(someValue));
			System.out.println(locale);
			System.out.println(something);
		};
	}

}
