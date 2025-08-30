package fr.kainovaii.portfolio.controller;

import fr.kainovaii.portfolio.component.RouteAccessChecker;
import fr.kainovaii.portfolio.service.ColorService;
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

    @ModelAttribute("siteName")
    public String siteName() {
        return "BlogSpring";
    }

    @ModelAttribute("homepage")
    public String homePage() {
        return "home";
    }

    @ModelAttribute("webAccess")
    public RouteAccessChecker webAccess() {
        return webAccess;
    }

    @ModelAttribute("colors")
    public Map<String, String> colors() { return colorService.getColors(); }
}