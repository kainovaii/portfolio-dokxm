package fr.kainovaii.portfolio.service;

import fr.kainovaii.portfolio.model.Color;
import fr.kainovaii.portfolio.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    public Map<String, String> getColors() {
        return colorRepository.findAll()
            .stream()
            .collect(Collectors.toMap(Color::getName, Color::getValue));
    }

    public String getColor(String name) {
        return colorRepository.findByName(name)
            .map(Color::getValue)
            .orElse("#000000");
    }
}