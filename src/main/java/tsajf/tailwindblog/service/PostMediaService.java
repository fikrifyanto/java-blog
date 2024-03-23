package tsajf.tailwindblog.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.model.Media;
import tsajf.tailwindblog.model.Post;
import tsajf.tailwindblog.repository.MediaRepository;
import tsajf.tailwindblog.utils.SecurityUtils;

import java.io.IOException;


@Service
public class PostMediaService {

    private final MediaRepository mediaRepository;

    private final UploadService uploadService;

    public PostMediaService(MediaRepository mediaRepository, UploadService uploadService) {
        this.mediaRepository = mediaRepository;
        this.uploadService = uploadService;
    }

    public void save(@ModelAttribute @Valid Post store, @RequestParam("file") MultipartFile file, Post post) throws IOException {
        String filePath = uploadService.save(file);
        Media media = new Media();
        media.setName(store.getTitle());
        media.setPath(filePath);
        media.setUrl(SecurityUtils.getBaseUrl(filePath));
        media = mediaRepository.save(media);

        post.setMedia(media);
    }


}
