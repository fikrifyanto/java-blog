package tsajf.tailwindblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.entity.Media;
import tsajf.tailwindblog.repository.MediaRepository;
import tsajf.tailwindblog.utils.SecurityUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public Media save(String name, MultipartFile file) throws IOException {
        Path directory = Paths.get("upload");

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path path = directory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(path);

        Media media = new Media();
        media.setName(name);
        media.setPath(String.valueOf(path));
        media.setUrl(SecurityUtils.getBaseUrl(String.valueOf(path)));

        return mediaRepository.save(media);
    }

    public void remove(Media media) throws IOException {
        Path path = Paths.get(media.getPath());
        Files.deleteIfExists(path);
    }


}
