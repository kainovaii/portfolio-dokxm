package fr.kainovaii.portfolio.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;

@Component
public class ResourceInitializer
{
    private final String[] defaultFiles = {
            "pages/home.yml",
            "pages/projects.yml"
    };

    @PostConstruct
    public void init() throws IOException
    {
        for (String resourcePath : defaultFiles) {
            copyIfNotExists(resourcePath);
        }
    }

    private void copyIfNotExists(String resourcePath) throws IOException
    {
        Path targetPath = Paths.get(resourcePath);
        if (Files.notExists(targetPath))
        {
            System.out.println("➡ Copie du fichier manquant: " + resourcePath);
            Files.createDirectories(targetPath.getParent());
            try (InputStream in = new ClassPathResource(resourcePath).getInputStream()) {
                Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
