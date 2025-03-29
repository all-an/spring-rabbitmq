package com.allan.proposal_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost/")
                .allowedMethods("*");
    }

    /**
     * @Bean The @Bean annotation in Java is used in Spring Framework to tell the application that a method will return
     * an object that should be managed as a Spring bean (a component controlled by Spring's dependency injection system).
     * This object returned can be used anywhere with @Autowired.
     * Also this annotation says this method will be called when the application run.
     * Those methods with @Bean annotation cannot be void.
     */
    @Bean
    public Locale getLocale() {
        return Locale.forLanguageTag("pt-BR");
    }

    @Bean
    public String printSomething() {
        String message = "something";
        System.out.println("something");
        return message;
    }
}
