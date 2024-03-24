package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tsajf.tailwindblog.repository.CommentRepository;

@Controller
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/admin/comment")
    public String getAllPosts(Model model) {
        model.addAttribute("page", "comment");
        model.addAttribute("comments", commentRepository.findAll());
        return "admin/comment/index";
    }

    @GetMapping("/admin/comment/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        commentRepository.deleteById(id);
        return "redirect:/admin/comment";
    }
}
