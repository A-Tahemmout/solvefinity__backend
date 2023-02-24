package ma.emsi.solvefinity.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    // Upload Image
    String store(MultipartFile file, String uploadDir, String filename) throws IOException;

    // Delete Image
    void delete(String filename, String uploadDir) throws IOException;

    // Delete All Images
    void deleteAll(String uploadDir) throws IOException;

    // Load Image
    byte[] load(String filename, String uploadDir) throws IOException;

    // Check if the directory exists
    boolean exists(Path path);
}
