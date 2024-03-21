package tsajf.tailwindblog.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/upload/{fileName:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        Path path = Paths.get("upload/" + fileName);
        byte[] imageData = Files.readAllBytes(path);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);
    }

}
