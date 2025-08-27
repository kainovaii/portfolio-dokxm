package fr.kainovaii.portfolio.controller.admin;

import fr.kainovaii.portfolio.service.PageService;
import fr.kainovaii.portfolio.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@PreAuthorize("hasRole('ADMIN')")
@Controller("adminPageController")
@RequestMapping("/admin/pages")
public class PageController
{
    private final PageService pageService;

    public PageController(PageService pageService) { this.pageService = pageService; }

    @GetMapping("")
    public String home(Model model)
    {
        model.addAttribute("pages", pageService.findAll());
        return "admin/pages/list";
    }

    @GetMapping("/edit/{name}")
    public String edit(@PathVariable String name, Model model)
    {
        model.addAttribute("pageName", name);
        return "admin/pages/edit";
    }

    @GetMapping("/new")
    public String create(Model model)
    {
        return "admin/pages/create";
    }
}