package fr.kainovaii.portfolio.controller;

import fr.kainovaii.portfolio.component.RouteAccessChecker;
import fr.kainovaii.portfolio.model.Post;
import fr.kainovaii.portfolio.model.Setting;
import fr.kainovaii.portfolio.service.ColorService;
import fr.kainovaii.portfolio.service.PostService;
import fr.kainovaii.portfolio.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice
{
    @Autowired
    private RouteAccessChecker webAccess;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private PostService postService;

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

    @ModelAttribute("allPosts")
    public List<Post> allPosts()
    {
        return postService.findAll().stream()
            .filter(post -> post.getStatus() == 1)
            .collect(Collectors.toList());
    }
}