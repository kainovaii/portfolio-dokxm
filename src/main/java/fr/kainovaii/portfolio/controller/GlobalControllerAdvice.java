package fr.kainovaii.portfolio.controller;

import fr.kainovaii.portfolio.component.RouteAccessChecker;
import fr.kainovaii.portfolio.model.Setting;
import fr.kainovaii.portfolio.service.ColorService;
import fr.kainovaii.portfolio.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice
{
    @Autowired
    private RouteAccessChecker webAccess;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SettingService settingService;

    @ModelAttribute("siteName")
    public String siteName() { return settingService.getSiteName(); }

    @ModelAttribute("homepage")
    public String homePage() {
        return settingService.getHomePage();
    }

    @ModelAttribute("colors")
    public Map<String, String> colors() { return colorService.getColors(); }

    @ModelAttribute("webAccess")
    public RouteAccessChecker webAccess() {
        return webAccess;
    }
}