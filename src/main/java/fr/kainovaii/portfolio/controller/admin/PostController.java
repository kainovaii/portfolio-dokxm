package fr.kainovaii.portfolio.controller.admin;

import com.github.slugify.Slugify;
import fr.kainovaii.portfolio.dto.PostWithAuthor;
import fr.kainovaii.portfolio.model.Post;
import fr.kainovaii.portfolio.model.User;
import fr.kainovaii.portfolio.service.PostService;
import fr.kainovaii.portfolio.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
@Controller("adminPostController")
@RequestMapping("/admin/posts")
public class PostController
{
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService)
    {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("")
    public String home(Model model)
    {
        List<Post> posts = postService.findAll();
        Set<Long> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toSet());
        Map<Long, String> authorNames = userService.getUsernamesByIds(authorIds);
        List<PostWithAuthor> enrichedPosts = posts.stream().map(post -> new PostWithAuthor(post, authorNames.getOrDefault(post.getAuthorId(),"Inconnu"))).collect(Collectors.toList());

        model.addAttribute("posts", enrichedPosts);
        return "admin/posts/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id)
    {
        return postService.findById(id)
        .map(post -> {
            model.addAttribute("post", post);
            return "admin/posts/edit";
        })
        .orElse("error/404");
    }

    @PostMapping("/edit")
    public RedirectView edit(
        @RequestParam long id,
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam int status,
        @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
        RedirectAttributes redirectAttributes,
        Slugify slugify) {

        postService.findById(id).ifPresentOrElse(post -> {
            post.setTitle(title);
            post.setContent(content);
            post.setSlug(slugify.slugify(title));
            post.setStatus(status);

            try {
                String uploadedFilename = handleFileUpload(thumbnail);
                if (uploadedFilename != null) {
                    post.setThumbnail(uploadedFilename);
                }
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorMessage", "File upload failed");
            }

            postService.save(post);
        }, () -> {
            throw new RuntimeException("Post not found with ID " + id);
        });

        redirectAttributes.addFlashAttribute("successMessage", "Post updated successfully");
        return new RedirectView("/admin/posts/edit/" + id);
    }

    @GetMapping("/new")
    public String create()
    {
        return "admin/posts/create";
    }

    @PostMapping("/new")
    public RedirectView create(
        @RequestParam String title,
        @RequestParam String content,
        @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal UserDetails userDetails,
        Slugify slugify) {

        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setSlug(slugify.slugify(title));
        newPost.setStatus(1);

        String username = userDetails.getUsername();

        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found : " + username));
        newPost.setAuthorId(user.getId());

        try {
            String uploadedFilename = handleFileUpload(thumbnail);
            if (uploadedFilename != null) {
                newPost.setThumbnail(uploadedFilename);
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "File upload failed");
            return new RedirectView("/admin/posts");
        }

        postService.save(newPost);
        redirectAttributes.addFlashAttribute("successMessage", "Post created successfully");
        return new RedirectView("/admin/posts");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable long id, RedirectAttributes redirectAttributes)
    {
        try {
            postService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Post with ID " + id + " not found.");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Success");
        return new RedirectView("/admin/posts");
    }

    private String handleFileUpload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String rootPath = System.getProperty("user.dir");
        File uploadDir = new File(rootPath + File.separator + "uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir, filename);
        file.transferTo(dest);

        return filename;
    }


}