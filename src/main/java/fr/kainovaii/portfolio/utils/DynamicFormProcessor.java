package fr.kainovaii.portfolio.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions;
import java.util.*;

public class DynamicFormProcessor
{

    private final Yaml yaml;

    public DynamicFormProcessor() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        this.yaml = new Yaml(options);
    }

    /**
     * Convertit automatiquement les données POST en structure YAML
     */
    public Map<String, Object> processFormData(HttpServletRequest request, Map<String, Object> originalPage) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> result = new LinkedHashMap<>();

        // Traiter le nom et les métadonnées de base
        if (parameterMap.containsKey("name")) {
            result.put("name", parameterMap.get("name")[0]);
        }

        // Traiter les composants dynamiquement
        Map<String, Object> components = new LinkedHashMap<>();
        Set<String> componentKeys = extractComponentKeys(parameterMap);

        for (String componentKey : componentKeys) {
            Map<String, Object> component = processComponent(componentKey, parameterMap);
            if (!component.isEmpty()) {
                components.put(componentKey, component);
            }
        }

        result.put("components", components);
        return result;
    }

    /**
     * Extrait automatiquement les clés des composants depuis les paramètres
     */
    private Set<String> extractComponentKeys(Map<String, String[]> parameterMap) {
        Set<String> componentKeys = new HashSet<>();

        for (String paramName : parameterMap.keySet()) {
            // Format attendu: components[componentName][property]
            if (paramName.startsWith("components[")) {
                int endBracket = paramName.indexOf(']');
                if (endBracket > 11) {
                    String componentKey = paramName.substring(11, endBracket);
                    componentKeys.add(componentKey);
                }
            }
        }

        return componentKeys;
    }

    /**
     * Traite un composant spécifique de manière dynamique
     */
    private Map<String, Object> processComponent(String componentKey, Map<String, String[]> parameterMap) {
        Map<String, Object> component = new LinkedHashMap<>();
        String prefix = "components[" + componentKey + "]";

        for (String paramName : parameterMap.keySet()) {
            if (paramName.startsWith(prefix + "[")) {
                String property = extractProperty(paramName, prefix);
                Object value = processValue(property, parameterMap.get(paramName));

                if (property.contains(".")) {
                    // Propriété imbriquée (ex: buttons.one.text)
                    setNestedProperty(component, property, value);
                } else {
                    component.put(property, value);
                }
            }
        }

        return component;
    }

    /**
     * Extrait le nom de la propriété depuis le nom du paramètre
     */
    private String extractProperty(String paramName, String prefix) {
        int start = prefix.length() + 1; // +1 pour le '['
        int end = paramName.lastIndexOf(']');
        return paramName.substring(start, end);
    }

    /**
     * Traite les valeurs en gérant les types automatiquement
     */
    private Object processValue(String property, String[] values) {
        if (values.length == 1) {
            String value = values[0];

            // Conversion automatique des types
            if ("true".equals(value) || "false".equals(value)) {
                return Boolean.parseBoolean(value);
            }

            if (value.matches("^\\d+$")) {
                return Integer.parseInt(value);
            }

            return value;
        } else {
            // Valeurs multiples (arrays)
            return Arrays.asList(values);
        }
    }

    /**
     * Gère les propriétés imbriquées comme buttons.one.text
     */
    private void setNestedProperty(Map<String, Object> map, String property, Object value) {
        String[] parts = property.split("\\.");
        Map<String, Object> current = map;

        for (int i = 0; i < parts.length - 1; i++) {
            String key = parts[i];
            if (!current.containsKey(key)) {
                current.put(key, new LinkedHashMap<String, Object>());
            }
            current = (Map<String, Object>) current.get(key);
        }

        current.put(parts[parts.length - 1], value);
    }

    /**
     * Convertit la structure en YAML
     */
    public String toYaml(Map<String, Object> data) {
        return yaml.dump(data);
    }
}