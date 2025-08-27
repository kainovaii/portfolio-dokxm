package fr.kainovaii.portfolio.config;

import fr.kainovaii.portfolio.model.Page;
import fr.kainovaii.portfolio.repository.PageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageInitializer {

    @Bean
    CommandLineRunner initDatabase(PageRepository pageRepository) {
        return args -> {
            if (pageRepository.count() == 0) {

                Page home = new Page();
                home.setTitle("Accueil");

                pageRepository.save(home);

                System.out.println("Page 'Accueil' initialisée en base.");
            }
        };
    }
}