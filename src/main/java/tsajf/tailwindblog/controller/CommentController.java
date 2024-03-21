package tsajf.tailwindblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tsajf.tailwindblog.entity.Post;
import tsajf.tailwindblog.repository.CommentRepository;
import tsajf.tailwindblog.repository.PostRepository;

@Controller
public class CommentController {

    @Autowired
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/admin/comment")
    public String getAllPosts(Model model) {
        model.addAttribute("title", "Comment List");
        model.addAttribute("page", "admin/comment/index");
        model.addAttribute("comments", commentRepository.findAll());
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/comment/{post_id}")
    public String seeComment(@PathVariable Integer post_id, Model model) {
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + post_id));
        model.addAttribute("title", "Post");
        model.addAttribute("page", "admin/comment/seePost");
        model.addAttribute("post", post);
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/comment/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        commentRepository.deleteById(id);
        return "redirect:/admin/comment";
    }
}
