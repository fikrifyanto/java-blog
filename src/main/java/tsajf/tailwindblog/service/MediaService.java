package tsajf.tailwindblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tsajf.tailwindblog.entity.Media;
import tsajf.tailwindblog.repository.MediaRepository;

import java.io.IOException;
import java.nio.file.Path;


@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UrlService urlService;

    public Media save(String name, MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        Path path = Path.of("upload/" + file.getOriginalFilename());
        file.transferTo(path);

        Media media = new Media();
        media.setName(name);
        media.setPath(String.valueOf(path));
        media.setUrl(urlService.getBaseUrl(String.valueOf(path)));

        return mediaRepository.save(media);
    }


}
