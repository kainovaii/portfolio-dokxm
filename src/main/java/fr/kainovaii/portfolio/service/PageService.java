package fr.kainovaii.portfolio.service;


import fr.kainovaii.portfolio.model.Page;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PageService
{
    public PageService()
    {
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
            return null; // on laisse le contrôleur décider -> 404
        }

        try (InputStream in = new FileInputStream(file)) {
            return yaml.load(in);
        }
    }

    public String save(String page)
    {
        return "";
    }
}