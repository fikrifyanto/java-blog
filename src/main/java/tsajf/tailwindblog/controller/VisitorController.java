package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tsajf.tailwindblog.model.Category;
import tsajf.tailwindblog.model.Post;
import tsajf.tailwindblog.repository.CategoryRepository;
import tsajf.tailwindblog.repository.PostRepository;
import tsajf.tailwindblog.repository.UserRepository;

import java.util.List;

@Controller
public class VisitorController {

    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;

    public VisitorController(CategoryRepository categoryRepository, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        List<Category> categories = categoryRepository.findFirst4ByOrderByName();
        List<Post> posts = postRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);

        return "visitor/index";
    }

}
