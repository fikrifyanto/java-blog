package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tsajf.tailwindblog.model.UserModel;
import tsajf.tailwindblog.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new UserModel());
        return "admin/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute UserModel user) {
        userService.register(user);
        return "redirect:/login?success";
    }

}
