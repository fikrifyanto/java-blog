package tsajf.tailwindblog.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tsajf.tailwindblog.entity.Media;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.MediaRepository;
import tsajf.tailwindblog.service.MediaService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Controller
public class MediaController {

    private final MediaRepository mediaRepository;

    private final MediaService mediaService;

    public MediaController(MediaRepository mediaRepository, MediaService mediaService) {
        this.mediaRepository = mediaRepository;
        this.mediaService = mediaService;
    }

    @GetMapping("/admin/media")
    public String index(Model model) {
        model.addAttribute("medias", mediaRepository.findAll());
        return "admin/media/index";
    }

    @GetMapping("/admin/media/create")
    public String create(Model model) {
        model.addAttribute("media", new Media());
        return "admin/media/create";
    }

    @PostMapping(value = "/admin/media/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String store(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute @Valid Media store,
            BindingResult result,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("media", store);
            return "admin/category/create";
        }

        mediaService.save(store.getName(), file);
        return "redirect:/admin/media";
    }

    @GetMapping("/admin/media/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("media", media);
        return "admin/media/edit";
    }

    @PostMapping("/admin/media/update/{id}")
    public String update(
            @PathVariable("id") Integer id,
            @ModelAttribute @Valid Media update,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("media", update);
            return "admin/media/edit";
        }

        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        media.setName(update.getName());
        mediaRepository.save(media);
        return "redirect:/admin/media";
    }

    @GetMapping("/admin/media/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<Media> media = mediaRepository.findById(id);

        if (media.isPresent()) {
            Path filepath = Path.of(media.get().getPath());
            File file = new File(String.valueOf(filepath));

            if (file.exists()) {
                boolean deleted = file.delete();
                if(!deleted) {
                    redirectAttributes.addFlashAttribute("error", "File is not exists in storage!");
                }
            }

            mediaRepository.deleteById(id);
            return "redirect:/admin/media";
        }

        return null;
    }
}
