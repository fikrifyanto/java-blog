package tsajf.tailwindblog.controller;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.config.UrlConfig;
import tsajf.tailwindblog.entity.Category;
import tsajf.tailwindblog.entity.Media;
import tsajf.tailwindblog.entity.Post;
import tsajf.tailwindblog.repository.PostRepository;
import tsajf.tailwindblog.repository.UserRepository;
import tsajf.tailwindblog.repository.CategoryRepository;
import tsajf.tailwindblog.repository.MediaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaRepository mediaRepository;

    private UrlConfig urlConfig;

    @Autowired
    ServletContext context;

    @GetMapping("/admin/post")
    public String index(Model model){
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
    public String create(@ModelAttribute Post post,
                         @RequestParam("categoryId") Integer categoryId,
                         @RequestParam("content") String content,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("title") String title,
                         @ModelAttribute("uploadForm") Media store) throws IOException {

//        Path path = Path.of("upload/" + file.getOriginalFilename());
//        file.transferTo(path);
//
//        Media media = new Media();
//        media.setName(title);
//        media.setPath(String.valueOf(path));
//        media.setUrl(urlConfig.getBaseUrl(String.valueOf(path)));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
        post.setUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        post.setCategory(category);
        post.setContent(content);
//        post.setMedia(media);
        postRepository.save(post);
        return "redirect:/admin/post";
    }

    @GetMapping("/admin/post/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("title", "Edit Post");
        model.addAttribute("page", "admin/post/edit");
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/fragments/layout";
    }

    @PostMapping("/admin/post/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute Post update,
                         @RequestParam("categoryId") Integer categoryId,
                         @RequestParam("content") String content,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("title") String title,
                         @ModelAttribute("uploadForm") Media store) throws IOException {

//        Path path = Path.of("upload/" + file.getOriginalFilename());
//        file.transferTo(path);
//
//        Media media = new Media();
//        media.setName(title);
//        media.setPath(String.valueOf(path));
//        media.setUrl(urlConfig.getBaseUrl(String.valueOf(path)));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + categoryId));
        post.setCategory(category);
        post.setTitle(update.getTitle());
        post.setDate(update.getDate());
        post.setContent(update.getContent());
//        post.setMedia(media);
        postRepository.save(post);
        return "redirect:/admin/post";
    }

    @GetMapping("/admin/post/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        postRepository.deleteById(id);
        return "redirect:/admin/post";
    }
}
