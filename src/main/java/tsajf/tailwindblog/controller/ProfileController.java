package tsajf.tailwindblog.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.model.User;
import tsajf.tailwindblog.repository.UserRepository;
import tsajf.tailwindblog.service.UploadService;
import tsajf.tailwindblog.utils.SecurityUtils;

import java.io.IOException;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    private final UploadService uploadService;

    public ProfileController(UserRepository userRepository, UploadService uploadService) {
        this.userRepository = userRepository;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/profile")
    public String index(Model model) {
        User user = SecurityUtils.getCurrentUser();
        model.addAttribute("user", user);
        return "admin/profile";
    }

    @PostMapping(value = "/admin/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String store(
            @ModelAttribute @Valid User update,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("user", update);
            return "admin/profile";
        }

        User user = userRepository.findById(update.getId()).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + update.getId()));

        user.setName(update.getName());
        user.setUsername(update.getUsername());
        user.setPassword(update.getPassword());

        if(!file.isEmpty()) {
            String filePath = uploadService.save(file);
            user.setImagePath(filePath);
            user.setImageUrl(SecurityUtils.getBaseUrl(filePath));
        }

        userRepository.save(user);
        return "redirect:/admin/profile";
    }

}
