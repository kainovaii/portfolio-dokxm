package fr.kainovaii.portfolio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import fr.kainovaii.portfolio.service.PageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@Controller
public class PageController
{
    private final PageService pageService;

    public PageController(PageService pageService) { this.pageService = pageService; }

    @GetMapping("/{name:^(?!favicon\\\\.ico$).+}")
    public String getPage(@PathVariable String name, Model model) throws IOException
    {
        Map<String, Object> page = pageService.findByName(name);
        if (page == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page '" + name + "' introuvable");
        }
        model.addAttribute("page", page);
        return "page";
    }
}
