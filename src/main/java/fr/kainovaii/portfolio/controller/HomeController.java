package fr.kainovaii.portfolio.controller;

import fr.kainovaii.portfolio.model.Page;
import fr.kainovaii.portfolio.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController
{
    private final PageRepository pageRepository;

    HomeController(PageRepository pageRepository)
    {
        this.pageRepository = pageRepository;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication)
    {
        return "home";
    }

    @GetMapping("/projects")
    public String projects(Model model, Authentication authentication)
    {
        return "projects";
    }

    @GetMapping("/{id}")
    public String pages(Model model, @PathVariable long id)
    {
        Page page = pageRepository.findById(id).orElseThrow(() -> new RuntimeException("Page not found"));

        return "page";
    }
}