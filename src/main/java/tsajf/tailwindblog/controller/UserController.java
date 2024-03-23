package tsajf.tailwindblog.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.UserRepository;
import tsajf.tailwindblog.service.UploadService;
import tsajf.tailwindblog.utils.SecurityUtils;

import java.io.IOException;

@Controller
public class UserController {

    private final UserRepository userRepository;

    private final UploadService uploadService;

    public UserController(UserRepository userRepository, UploadService uploadService) {
        this.userRepository = userRepository;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/user")
    public String index(Model model) {
        model.addAttribute("page", "user");
        model.addAttribute("users", userRepository.findAll());
        return "admin/user/index";
    }

    @GetMapping("/admin/user/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String store(
            @ModelAttribute @Valid User store,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("user", store);
            return "admin/user/create";
        }

        String filePath = uploadService.save(file);

        store.setImagePath(filePath);
        store.setImageUrl(SecurityUtils.getBaseUrl(filePath));
        userRepository.save(store);

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "admin/user/edit";
    }

    @PostMapping(value = "/admin/user/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String update(
            @PathVariable("id") Integer id,
            @ModelAttribute @Valid User update,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("user", update);
            return "admin/user/edit";
        }

        String filePath = uploadService.save(file);

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setName(update.getName());
        user.setUsername(update.getUsername());
        user.setPassword(update.getPassword());
        user.setImagePath(filePath);
        user.setImageUrl(SecurityUtils.getBaseUrl(filePath));
        userRepository.save(user);

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/user";
    }

}
