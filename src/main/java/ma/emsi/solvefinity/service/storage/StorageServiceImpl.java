package ma.emsi.solvefinity.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${solvefinity.app.upload.path}")
    private String rootLocation;

    @Override
    public String store(MultipartFile file, String uploadDir, String filename) throws IOException {
        // Check if the file is empty
        if (file.isEmpty()) {
            return filename;
        }

        // Initialize the path
        Path path = Paths.get(rootLocation);

        // Check if the directory exists
        if (!exists(path)) {
            throw new IOException("Could not initialize storage!");
        }

        // Generate the filename
        filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Copy the file to the target location
        Files.copy(file.getInputStream(), path.resolve(uploadDir + filename));

        // Return the file name
        return filename;
    }

    @Override
    public void delete(String filename, String uploadDir) throws IOException {
        // Initialize the path
        Path path = Paths.get(rootLocation + uploadDir);

        // Check if the file exists
        if (!exists(path)) {
            throw new IOException("Could not find the file!");
        }

        // Delete the file
        Files.delete(path.resolve(filename));
    }

    @Override
    public void deleteAll(String uploadDir) throws IOException {
        // Initialize the path
        Path path = Paths.get(rootLocation + uploadDir);

        // Check if the directory exists
        if (!exists(path)) {
            throw new IOException("Could not initialize storage!");
        }

        // Delete the directory
        Files.walk(path)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Override
    public byte[] load(String filename, String uploadDir) throws IOException {
        // Initialize the path
        Path path = Paths.get(rootLocation + uploadDir);

        // Check if the file exists
        if (!exists(path)) {
            throw new IOException("Could not find the file!");
        }

        // Return the file
        return Files.readAllBytes(path.resolve(filename));
    }

    @Override
    public boolean exists(Path path) {
        return Files.exists(path);
    }
}
