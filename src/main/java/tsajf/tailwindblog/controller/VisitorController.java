package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitorController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Home");
        model.addAttribute("page", "visitor/index");
        return "visitor/fragments/layout";
    }
}
