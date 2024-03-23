package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tsajf.tailwindblog.model.Post;
import tsajf.tailwindblog.repository.CommentRepository;
import tsajf.tailwindblog.repository.PostRepository;

@Controller
public class CommentController {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public CommentController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/admin/comment")
    public String getAllPosts(Model model) {
        model.addAttribute("page", "comment");
        model.addAttribute("comments", commentRepository.findAll());
        return "admin/comment/index";
    }

    @GetMapping("/admin/comment/{post_id}")
    public String seeComment(@PathVariable Integer post_id, Model model) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + post_id));
        model.addAttribute("post", post);
        return "admin/comment/seePost";
    }

    @GetMapping("/admin/comment/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        commentRepository.deleteById(id);
        return "redirect:/admin/comment";
    }
}
