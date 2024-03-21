package tsajf.tailwindblog.controller;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.config.UrlConfig;
import tsajf.tailwindblog.entity.Media;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.MediaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Controller
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;

    private UrlConfig urlConfig;

    @Autowired
    ServletContext context;

    @GetMapping("/admin/media")
    public String index(Model model) {
        model.addAttribute("title", "Media List");
        model.addAttribute("page", "admin/media/index");
        model.addAttribute("medias", mediaRepository.findAll());
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/media/create")
    public String create(Model model) {
        model.addAttribute("title", "Add Media");
        model.addAttribute("page", "admin/media/create");
        model.addAttribute("media", new Media());
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/media/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("title", "Edit Media");
        model.addAttribute("page", "admin/media/edit");
        model.addAttribute("media", media);
        return "admin/fragments/layout";
    }

    @PostMapping("/admin/media/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute User update) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        media.setName(update.getName());
        mediaRepository.save(media);
        return "redirect:/admin/media";
    }

    @PostMapping(value = "/admin/media/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String store(@RequestParam("file") MultipartFile file, @ModelAttribute("uploadForm") Media store) throws IOException {
        Path path = Path.of("upload/" + file.getOriginalFilename());
        file.transferTo(path);

        Media media = new Media();
        media.setName(store.getName());
        media.setPath(String.valueOf(path));
        media.setUrl(urlConfig.getBaseUrl(String.valueOf(path)));

        mediaRepository.save(media);

        return "redirect:/admin/media";
    }

    @GetMapping("/admin/media/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Optional<Media> media = mediaRepository.findById(id);

        if(media.isPresent()) {
            Path filepath = Path.of(media.get().getPath());
            File file = new File(String.valueOf(filepath));
            System.out.println(file);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.out.println("Failed to delete the file.");
                }
            } else {
                System.out.println("File not found.");
            }
        }

        mediaRepository.deleteById(id);
        return "redirect:/admin/media";
    }

}
