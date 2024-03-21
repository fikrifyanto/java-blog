package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/admin/post")
    public void halamanPost(Model model){
        model.addAttribute("title", "User List");
    }
}
