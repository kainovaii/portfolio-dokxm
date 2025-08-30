package fr.kainovaii.portfolio.service;

import fr.kainovaii.portfolio.model.Color;
import fr.kainovaii.portfolio.model.Setting;
import fr.kainovaii.portfolio.repository.ColorRepository;
import fr.kainovaii.portfolio.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SettingService
{
    @Autowired
    private SettingRepository settingRepository;

    public String getSiteName()
    {
        return settingRepository.findAll()
            .stream()
            .findFirst()
            .map(Setting::getSiteName)
            .orElse("");
    }

    public String getHomePage()
    {
        return settingRepository.findAll()
            .stream()
            .findFirst()
            .map(Setting::getHomePage)
            .orElse("");
    }

}