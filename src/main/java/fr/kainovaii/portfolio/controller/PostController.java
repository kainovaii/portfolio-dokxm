package fr.kainovaii.portfolio.controller;

import fr.kainovaii.portfolio.model.Post;
import fr.kainovaii.portfolio.model.User;
import fr.kainovaii.portfolio.service.PostService;
import fr.kainovaii.portfolio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController
{
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping()
    public String allPosts(Model model)
    {
        List<Post> posts = postService.findAll().stream()
                .filter(post -> post.getStatus() == 1)
                .collect(Collectors.toList());

        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/{slug}")
    public String showPost(Model model, @PathVariable String slug)
    {
        return postService.findBySlug(slug)
        .map(post -> {
            User author = userService.findById(post.getAuthorId()).orElse(null);
            String date = post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            model.addAttribute("post", post);
            model.addAttribute("createdAt", date);
            model.addAttribute("authorUsername", author != null ? author.getUsername() : "Inconnu");
            model.addAttribute("authorRole", author != null ? author.getRole() : "Inconnu");

            if (post.getStatus() == 0) {
                return "redirect:/posts";
            }
            return "posts/show";
        })
        .orElse("error/404");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id)
    {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id)
    {
        return postService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
}