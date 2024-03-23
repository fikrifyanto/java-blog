package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tsajf.tailwindblog.model.Category;
import tsajf.tailwindblog.model.Post;
import tsajf.tailwindblog.repository.CategoryRepository;
import tsajf.tailwindblog.repository.PostRepository;
import tsajf.tailwindblog.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/category/{id}")
    public String category(@PathVariable("id") Integer id, Model model) {

        List<Category> categories = categoryRepository.findFirst4ByOrderByName();
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(optionalCategory.isEmpty()) {
            return "redirect:/";
        }

        Category category = optionalCategory.get();

        model.addAttribute("categories", categories);
        model.addAttribute("category", category);

        return "visitor/category";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable("id") Integer id, Model model) {

        List<Category> categories = categoryRepository.findFirst4ByOrderByName();
        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isEmpty()) {
            return "redirect:/";
        }

        Post post = optionalPost.get();

        model.addAttribute("categories", categories);
        model.addAttribute("post", post);

        return "visitor/post";
    }

}
