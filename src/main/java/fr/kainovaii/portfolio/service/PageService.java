package fr.kainovaii.portfolio.service;

import fr.kainovaii.portfolio.utils.DynamicFormProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PageService
{

    private final DynamicFormProcessor formProcessor;
    private final String pagesDirectory = "pages/";

    public PageService()
    {
        this.formProcessor = new DynamicFormProcessor();
        YamlPropertiesFactoryBean loadConfiguration = new YamlPropertiesFactoryBean();
    }

    public List<String> findAll()
    {
        File folder = new File("./pages"); // ou la racine du jar
        if (!folder.exists()) return Collections.emptyList();

        return Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(f -> f.getName().endsWith(".yml"))
                .map(f -> f.getName().replace(".yml", ""))
                .collect(Collectors.toList());
    }

    public Map<String, Object> findByName(String name) throws IOException
    {
        Yaml yaml = new Yaml();
        String baseDir = System.getProperty("user.dir");
        File file = new File(baseDir, "pages/" + name + ".yml");

        if (!file.exists()) {
            return null;
        }

        try (InputStream in = new FileInputStream(file)) {
            return yaml.load(in);
        }
    }

    public void savePage(String pageName, HttpServletRequest request) throws IOException
    {
        // Charger la page existante pour préserver la structure
        Map<String, Object> originalPage = findByName(pageName);

        // Traitement automatique des données du formulaire
        Map<String, Object> pageData = formProcessor.processFormData(request, originalPage);

        // Conversion en YAML
        String yamlContent = formProcessor.toYaml(pageData);

        // Sauvegarde dans le fichier
        Path filePath = Paths.get(pagesDirectory + pageName + ".yml");
        Files.write(filePath, yamlContent.getBytes());
    }

    public String save(String page)
    {
        return "";
    }
}