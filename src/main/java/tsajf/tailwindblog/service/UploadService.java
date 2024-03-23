package tsajf.tailwindblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UploadService {

    public String save(MultipartFile file) throws IOException {
        Path directory = Paths.get("upload");

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path path = directory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(path);

        return String.valueOf(path);
    }

}
