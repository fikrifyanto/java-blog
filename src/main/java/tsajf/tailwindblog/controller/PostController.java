package tsajf.tailwindblog.controller;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.entity.Media;
import tsajf.tailwindblog.entity.Post;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.PostRepository;
import tsajf.tailwindblog.repository.UserRepository;
import tsajf.tailwindblog.repository.CategoryRepository;
import tsajf.tailwindblog.service.MediaService;
import tsajf.tailwindblog.utils.SecurityUtils;

import java.io.IOException;
import java.util.Objects;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaService mediaService;

    @Autowired
    ServletContext context;

    @GetMapping("/admin/post")
    public String index(Model model) {
        model.addAttribute("title", "Post List");
        model.addAttribute("page", "admin/post/index");
        model.addAttribute("posts", postRepository.findAll());
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/post/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("title", "Add Post");
        model.addAttribute("page", "admin/post/create");
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/fragments/layout";
    }

    @PostMapping(value = "/admin/post/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@ModelAttribute Post store, @RequestParam("file") MultipartFile file) throws IOException {
        Media media = mediaService.save(store.getTitle(), file);
        User user = userRepository.findByUsername(Objects.requireNonNull(SecurityUtils.getCurrentUser()).getUsername());

        Post post = new Post();
        post.setUser(user);
        post.setTitle(store.getTitle());
        post.setDate(store.getDate());
        post.setCategory(store.getCategory());
        post.setContent(store.getContent());
        post.setMedia(media);

        postRepository.save(post);

        return "redirect:/admin/post";
    }

    @GetMapping("/admin/post/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("title", "Edit Post");
        model.addAttribute("page", "admin/post/edit");
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/fragments/layout";
    }

    @PostMapping("/admin/post/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute Post store, @RequestParam("file") MultipartFile file) throws IOException {
        User user = userRepository.findByUsername(Objects.requireNonNull(SecurityUtils.getCurrentUser()).getUsername());

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        post.setUser(user);
        post.setTitle(store.getTitle());
        post.setDate(store.getDate());
        post.setCategory(store.getCategory());
        post.setContent(store.getContent());

        if(!file.isEmpty()) {
            mediaService.remove(post.getMedia());
            Media media = mediaService.save(store.getTitle(), file);
            post.setMedia(media);
        }

        postRepository.save(post);

        return "redirect:/admin/post";
    }

    @GetMapping("/admin/post/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        postRepository.deleteById(id);
        return "redirect:/admin/post";
    }

}
