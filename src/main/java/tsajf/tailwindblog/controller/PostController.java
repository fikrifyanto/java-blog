package tsajf.tailwindblog.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.entity.Media;
import tsajf.tailwindblog.entity.Post;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.MediaRepository;
import tsajf.tailwindblog.repository.PostRepository;
import tsajf.tailwindblog.repository.UserRepository;
import tsajf.tailwindblog.repository.CategoryRepository;
import tsajf.tailwindblog.service.UploadService;
import tsajf.tailwindblog.utils.SecurityUtils;

import java.io.IOException;
import java.util.Objects;

@Controller
public class PostController {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final MediaRepository mediaRepository;

    private final UploadService uploadService;


    public PostController(PostRepository postRepository, CategoryRepository categoryRepository, UserRepository userRepository, MediaRepository mediaRepository, UploadService uploadService) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/post")
    public String index(Model model) {
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

        User user = userRepository.findByUsername(Objects.requireNonNull(SecurityUtils.getCurrentUser()).getUsername());

        Post post = new Post();
        post.setUser(user);
        post.setTitle(store.getTitle());
        post.setDate(store.getDate());
        post.setCategory(store.getCategory());
        post.setContent(store.getContent());

        String filePath = uploadService.save(file);
        Media media = new Media();
        media.setName(store.getTitle());
        media.setPath(filePath);
        media.setUrl(SecurityUtils.getBaseUrl(filePath));
        media = mediaRepository.save(media);

        post.setMedia(media);

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

        User user = userRepository.findByUsername(Objects.requireNonNull(SecurityUtils.getCurrentUser()).getUsername());

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        post.setUser(user);
        post.setTitle(update.getTitle());
        post.setDate(update.getDate());
        post.setCategory(update.getCategory());
        post.setContent(update.getContent());

        if (!file.isEmpty()) {
            String filePath = uploadService.save(file);
            Media media = new Media();
            media.setName(update.getTitle());
            media.setPath(filePath);
            media.setUrl(SecurityUtils.getBaseUrl(filePath));
            media = mediaRepository.save(media);

            post.setMedia(media);
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
