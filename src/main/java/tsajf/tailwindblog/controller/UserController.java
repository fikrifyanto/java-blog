package tsajf.tailwindblog.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.UserRepository;
import tsajf.tailwindblog.utils.SecurityUtils;
import tsajf.tailwindblog.utils.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/user")
    public String index(Model model) {
        model.addAttribute("title", "User List");
        model.addAttribute("page", "admin/user/index");
        model.addAttribute("users", userRepository.findAll());
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/user/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Add User");
        model.addAttribute("page", "admin/user/create");
        return "admin/fragments/layout";
    }

    @PostMapping(value = "/admin/user/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String store(@ModelAttribute @Valid User store,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", ValidationUtils.getErrorMessages(bindingResult));
            return "redirect:/admin/user/create";
        }

        Path directory = Paths.get("upload");

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path path = directory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(path);

        store.setImagePath(String.valueOf(path));
        store.setImageUrl(SecurityUtils.getBaseUrl(String.valueOf(path)));

        userRepository.save(store);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("title", "Edit User");
        model.addAttribute("page", "admin/user/edit");
        model.addAttribute("user", user);
        return "admin/fragments/layout";
    }

    @PostMapping(value = "/admin/user/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String update(
            @PathVariable("id") Integer id,
            @ModelAttribute @Valid User update,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", ValidationUtils.getErrorMessages(bindingResult));
            return "redirect:/admin/user/edit/" + id;
        }

        Path directory = Paths.get("upload");

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path path = directory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(path);

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setName(update.getName());
        user.setUsername(update.getUsername());
        user.setPassword(update.getPassword());
        user.setImagePath(String.valueOf(path));
        user.setImageUrl(SecurityUtils.getBaseUrl(String.valueOf(path)));
        userRepository.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/user";
    }

}
