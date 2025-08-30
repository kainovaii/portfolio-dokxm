package fr.kainovaii.portfolio.controller;

import fr.kainovaii.portfolio.model.Page;
import fr.kainovaii.portfolio.service.PageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("")
public class HomeController
{
    private final PageService pageService;

    public HomeController(PageService pageService) { this.pageService = pageService; }


    @GetMapping("/")
    public String home(Model model, Authentication authentication) throws IOException
    {
        String name = (String)  model.getAttribute("homepage");
        Map<String, Object> page = pageService.findByName(name);
        model.addAttribute("page", page);
        return "page";
    }
}