package fr.kainovaii.portfolio.controller.admin;

import fr.kainovaii.portfolio.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@PreAuthorize("hasRole('ADMIN')")
@Controller("adminUserController")
@RequestMapping("/admin/users")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "admin/users/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id)
    {
        return userService.findById(id)
        .map(user -> {
            model.addAttribute("user", user);
            return "admin/users/edit";
        })
        .orElse("error/404");
    }

    @PostMapping("/edit")
    public RedirectView edit(
        @RequestParam long id,
        @RequestParam String username,
        @RequestParam String email,
        @RequestParam String role,
        RedirectAttributes redirectAttributes) {

        userService.findById(id).ifPresentOrElse(user -> {
            user.setUsername(username);
            user.setEmail(email);
            user.setRole(role);
            userService.save(user);
        }, () -> {
            throw new RuntimeException("User not found with ID " + id);
        });

        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
        return new RedirectView("/admin/users/edit/" + id);
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable long id, RedirectAttributes redirectAttributes)
    {
        try {
            userService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Users with ID " + id + " not found.");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Success");
        return new RedirectView("/admin/users");
    }
}