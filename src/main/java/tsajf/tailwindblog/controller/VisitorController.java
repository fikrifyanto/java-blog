package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tsajf.tailwindblog.model.Category;
import tsajf.tailwindblog.model.Comment;
import tsajf.tailwindblog.model.Post;
import tsajf.tailwindblog.repository.CategoryRepository;
import tsajf.tailwindblog.repository.CommentRepository;
import tsajf.tailwindblog.repository.PostRepository;
import tsajf.tailwindblog.utils.StringUtils;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Controller
public class VisitorController {

    private final CategoryRepository categoryRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public VisitorController(CategoryRepository categoryRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        List<Category> categories = categoryRepository.findFirst4ByOrderByName();
        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            String excerpt = StringUtils.excerpt(post.getContent());
            post.setContent(excerpt);
        }

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

    @PostMapping("/comment/{id}")
    public String comment(@PathVariable("id") Integer id, @ModelAttribute Comment storeComment) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isEmpty()) {
            return "redirect:/";
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        Comment comment = new Comment();
        comment.setDate(date);
        comment.setPost(optionalPost.get());
        comment.setName(storeComment.getName());
        comment.setEmail(storeComment.getEmail());
        comment.setContent(storeComment.getContent());
        commentRepository.save(comment);

        return "redirect:/post/" + id;
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable("id") Integer id, Model model) {

        List<Category> categories = categoryRepository.findFirst4ByOrderByName();
        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isEmpty()) {
            return "redirect:/";
        }

        Post post = optionalPost.get();
        Comment comment = new Comment();

        model.addAttribute("categories", categories);
        model.addAttribute("post", post);
        model.addAttribute("comment", comment);

        return "visitor/post";
    }

}
