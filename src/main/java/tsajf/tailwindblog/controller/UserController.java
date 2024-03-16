package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/admin/user")
    public String index(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("page", "admin/index");
        return "admin/fragments/layout";
    }
}
