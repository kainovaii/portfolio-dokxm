package fr.kainovaii.portfolio.controller.admin;

import fr.kainovaii.portfolio.service.PageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@PreAuthorize("hasRole('ADMIN')")
@Controller("adminPageController")
@RequestMapping("/admin/pages")
public class PageController
{
    private final PageService pageService;
    private static final String PAGES_DIR = "pages/";

    public PageController(PageService pageService) { this.pageService = pageService; }

    @GetMapping("")
    public String home(Model model)
    {
        model.addAttribute("pages", pageService.findAll());
        return "admin/pages/list";
    }

    @GetMapping("/edit/{name}")
    public String edit(@PathVariable String name, Model model) throws IOException
    {
        Map<String, Object> page = pageService.findByName(name);
        model.addAttribute("page", page);
        model.addAttribute("pageName", name);
        return "admin/pages/edit";
    }

    @PostMapping("/save/{pageName}")
    public String savePage(
            @PathVariable String pageName,
            @RequestParam Map<String, String> params
    ) throws IOException {
        // 1️⃣ Lire l'ancien YAML pour ne pas perdre d'autres composants
        File yamlFile = new File(PAGES_DIR + pageName + ".yml");
        Map<String, Object> yamlData = new LinkedHashMap<>();
        Yaml yaml = new Yaml();

        if (yamlFile.exists()) {
            Object loaded = yaml.load(java.nio.file.Files.newInputStream(yamlFile.toPath()));
            if (loaded instanceof Map) {
                yamlData = (Map<String, Object>) loaded;
            }
        } else {
            yamlData.put("name", pageName);
            yamlData.put("components", new LinkedHashMap<String, Object>());
        }

        // 2️⃣ Construire la structure components dynamiquement à partir des paramètres
        Map<String, Object> components = (Map<String, Object>) yamlData.getOrDefault("components", new LinkedHashMap<>());

        Map<String, Map<String, Object>> nested = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // On ne traite que les clés qui commencent par "components["
            if (!key.startsWith("components[")) continue;

            // Ex: components[hero][buttons][one][text]
            String[] path = key.replaceFirst("components\\[", "")
                    .replaceAll("]", "")
                    .split("\\[");

            // Création récursive du chemin (dans 'nested')
            Map<String, Object> current = (Map<String, Object>) nested.computeIfAbsent(path[0], k -> new LinkedHashMap<>());
            for (int i = 1; i < path.length - 1; i++) {
                current = (Map<String, Object>) current.computeIfAbsent(path[i], k -> new LinkedHashMap<>());
            }

            // Dernier élément = valeur
            current.put(path[path.length - 1], parseValue(value));
        }

        // 3️⃣ Fusionner le composant modifié dans la map complète (deep merge)
        for (Map.Entry<String, Map<String, Object>> e : nested.entrySet()) {
            String compKey = e.getKey();
            Map<String, Object> newPart = e.getValue();

            if (components.containsKey(compKey) && components.get(compKey) instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> existing = (Map<String, Object>) components.get(compKey);
                deepMerge(existing, newPart); // conserve les clés absentes du formulaire (ex: type)
            } else {
                components.put(compKey, new LinkedHashMap<>(newPart));
            }
        }

        yamlData.put("components", components);

        // 4️⃣ Sauvegarder le YAML formaté proprement
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndent(2);
        Yaml yamlWriter = new Yaml(options);

        try (FileWriter writer = new FileWriter(yamlFile)) {
            yamlWriter.dump(yamlData, writer);
        }

        return "redirect:/admin/pages/edit/" + pageName + "?saved=true";
    }

    /**
     * Fusionne récursivement 'source' dans 'target'. Si une clé existe dans les deux et que les deux valeurs
     * sont des Maps, on fusionne récursivement. Sinon on écrase (ou crée).
     */
    @SuppressWarnings("unchecked")
    private void deepMerge(Map<String, Object> target, Map<String, Object> source) {
        for (Map.Entry<String, Object> e : source.entrySet()) {
            String k = e.getKey();
            Object sv = e.getValue();
            Object tv = target.get(k);
            if (sv instanceof Map && tv instanceof Map) {
                deepMerge((Map<String, Object>) tv, (Map<String, Object>) sv);
            } else {
                // remplace ou créé la valeur (si sv est Map, elle est copiée telle quelle)
                target.put(k, sv);
            }
        }
    }

    /**
     * Convertit les valeurs simples en types corrects :
     * - "true"/"false" → boolean
     * - nombres → Integer
     * - sinon string
     */
    private Object parseValue(String v) {
        if (v == null) return null;
        if (v.equalsIgnoreCase("true") || v.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(v);
        }
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException ignored) {}
        return v.trim();
    }
}

