package fr.kainovaii.portfolio.config;

import fr.kainovaii.portfolio.model.Color;
import fr.kainovaii.portfolio.repository.ColorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThemeInitializer
{
    @Bean
    CommandLineRunner initDatabase(ColorRepository colorRepository)
    {
        return args -> {
            if (colorRepository.count() == 0) {
                Color dark = new Color();
                dark.setName("dark");
                dark.setValue("#0a0a0a");

                Color darkSecondary = new Color();
                darkSecondary.setName("dark-secondary");
                darkSecondary.setValue("#1a1a1a");

                Color darkCard = new Color();
                darkCard.setName("dark-card");
                darkCard.setValue("#222222");

                Color accentOrange = new Color();
                accentOrange.setName("accent-orange");
                accentOrange.setValue("#0A51C7");

                Color accentYellow = new Color();
                accentYellow.setName("accent-yellow");
                accentYellow.setValue("#4AC3EE");

                Color textGray = new Color();
                textGray.setName("text-gray");
                textGray.setValue("#999999");

                colorRepository.save(dark);
                colorRepository.save(darkSecondary);
                colorRepository.save(darkCard);
                colorRepository.save(accentOrange);
                colorRepository.save(accentYellow);
                colorRepository.save(textGray);
            }
        };
    }
}
