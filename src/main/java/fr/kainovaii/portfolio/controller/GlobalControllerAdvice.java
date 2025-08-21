package fr.kainovaii.portfolio.controller;

import fr.kainovaii.portfolio.component.RouteAccessChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice
{
    @Autowired
    private RouteAccessChecker webAccess;

    @ModelAttribute("siteName")
    public String siteName() {
        return "BlogSpring";
    }

    @ModelAttribute("webAccess")
    public RouteAccessChecker webAccess() {
        return webAccess;
    }
}