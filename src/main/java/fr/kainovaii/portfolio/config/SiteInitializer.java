package fr.kainovaii.portfolio.config;

import fr.kainovaii.portfolio.model.Color;
import fr.kainovaii.portfolio.model.Setting;
import fr.kainovaii.portfolio.model.User;
import fr.kainovaii.portfolio.repository.ColorRepository;
import fr.kainovaii.portfolio.repository.SettingRepository;
import fr.kainovaii.portfolio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteInitializer
{
    @Bean
    CommandLineRunner initDatabase(
        ColorRepository colorRepository,
        SettingRepository settingRepository,
        UserRepository userRepository
    )
    {
        return args ->
        {
            if (settingRepository.count() == 0)
            {
                Setting setting = new Setting();
                setting.setHomePage("home");
                setting.setSiteName("Portfolio");
                settingRepository.saveAndFlush(setting);
            }

            if (colorRepository.count() == 0)
            {
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

            if (userRepository.count() == 0)
            {
                User user = new User();
                user.setUsername("Admin");
                user.setEmail("admin@test.local");
                user.setPassword("$2y$10$6GKWeHgW70WK1yn.MNCgVuKesSOTkijrn6Yc5ay73XxnJYiUvB3Zi");
                user.setRole("ADMIN");
                userRepository.saveAndFlush(user);
            }
        };
    }
}
