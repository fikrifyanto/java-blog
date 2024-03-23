package tsajf.tailwindblog.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.model.Post;
import tsajf.tailwindblog.model.User;
import tsajf.tailwindblog.repository.PostRepository;
import tsajf.tailwindblog.repository.CategoryRepository;
import tsajf.tailwindblog.service.PostMediaService;
import tsajf.tailwindblog.utils.SecurityUtils;

import java.io.IOException;

@Controller
public class PostController {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final PostMediaService postMediaService;


    public PostController(PostRepository postRepository, CategoryRepository categoryRepository, PostMediaService postMediaService) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.postMediaService = postMediaService;
    }

    @GetMapping("/admin/post")
    public String index(Model model) {
        model.addAttribute("page", "post");
        model.addAttribute("posts", postRepository.findAll());
        return "admin/post/index";
    }

    @GetMapping("/admin/post/create")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/post/create";
    }

    @PostMapping(value = "/admin/post/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String store(
            @ModelAttribute @Valid Post store,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            System.out.println(store.getTitle());
            model.addAttribute("post", store);
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/post/create";
        }

        User user = SecurityUtils.getCurrentUser();

        Post post = new Post();
        post.setUser(user);
        post.setTitle(store.getTitle());
        post.setDate(store.getDate());
        post.setCategory(store.getCategory());
        post.setContent(store.getContent());

        postMediaService.save(store, file, post);

        postRepository.save(post);

        return "redirect:/admin/post";
    }

    @GetMapping("/admin/post/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/post/edit";
    }

    @PostMapping("/admin/post/update/{id}")
    public String update(
            @PathVariable("id") Integer id,
            @ModelAttribute Post update,
            @RequestParam("file") MultipartFile file,
            BindingResult result,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("post", update);
            return "admin/post/edit";
        }

        User user = SecurityUtils.getCurrentUser();

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        post.setUser(user);
        post.setTitle(update.getTitle());
        post.setDate(update.getDate());
        post.setCategory(update.getCategory());
        post.setContent(update.getContent());

        if (!file.isEmpty()) {
            postMediaService.save(update, file, post);
        } else {
            post.setMedia(null);
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
