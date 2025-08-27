package fr.kainovaii.portfolio.controller.admin;

import fr.kainovaii.portfolio.service.PostService;
import fr.kainovaii.portfolio.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
@Controller
@RequestMapping("/admin")
public class DashboardController
{
    private final PostService postService;
    private final UserService userService;

    public DashboardController(PostService postService, UserService userService)
    {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("")
    public String home(Model model)
    {
        model.addAttribute("postsCount", postService.count());
        model.addAttribute("usersCount", userService.count());
        return "admin/dashboard";
    }
}