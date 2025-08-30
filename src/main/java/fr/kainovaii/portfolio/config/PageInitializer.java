package fr.kainovaii.portfolio.config;

import fr.kainovaii.portfolio.model.Page;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageInitializer {

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

        };
    }
}