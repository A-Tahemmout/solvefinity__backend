package ma.emsi.solvefinity.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.vo.LoanerVO;
import ma.emsi.solvefinity.service.LoanerService;
import ma.emsi.solvefinity.service.auth.AuthenticationService;
import ma.emsi.solvefinity.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loaner")
@RequiredArgsConstructor
public class LoanerController {
    private static final String UPLOAD_DIR = "images/loaners/";
    private static final String DEFAULT_IMAGE = "loaner-icon.png";
    private final LoanerService loanerService;
    private final AuthenticationService authenticationService;
    private final StorageService storageService;
    @Value("${solvefinity.app.admin-bank-slug}")
    private String adminBankSlug;

    @GetMapping("/all")
    public ResponseEntity<List<LoanerVO>> getAllLoaners() {
        // Get Auth user
        var user = authenticationService.getCurrentUser();

        // Check if user is admin
        if (user.getBank().getSlug().equals(adminBankSlug)) {
            // Return all loaners
            return ResponseEntity.ok(loanerService.getAll());
        }

        // Return loaners by bank slug
        return ResponseEntity.ok(loanerService.getAllByBankSlug(user.getBank().getSlug()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanerVO> getLoanerById(@PathVariable UUID id) {
        // Get loaner by id
        LoanerVO loanerVo = loanerService.getById(id);

        // Check if loaner was found
        if (loanerVo == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Return loaner
        return ResponseEntity.ok(loanerVo);
    }

    @GetMapping("/cin/{cin}")
    public ResponseEntity<LoanerVO> getLoanerByCin(@PathVariable String cin) {
        // Get loaner by cin
        LoanerVO loanerVo = loanerService.getByCin(cin);

        // Check if loaner was found
        if (loanerVo == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Return loaner
        return ResponseEntity.ok(loanerVo);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LoanerVO> createLoaner(@RequestPart("loaner") LoanerVO loaner,
                                                 @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        try {
            // Check if avatar is not null
            if (avatar != null) {
                // Save the loaner avatar
                String filename = storageService.store(avatar, UPLOAD_DIR, DEFAULT_IMAGE);

                // Set avatar filename
                loaner.setAvatar(filename);
            }

            // Get current user
            var user = authenticationService.getCurrentUser();

            // Set loaner bank
            loaner.setBank(user.getBank());

            // Save loaner
            loaner = loanerService.save(loaner);

            // Return loaner
            return ResponseEntity.ok(loaner);
        } catch (IOException e) {
            e.printStackTrace();
            // Return internal server error
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LoanerVO> updateLoaner(@RequestPart("loaner") LoanerVO loaner,
                                                 @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        try {
            // Check if avatar is not null
            if (avatar != null) {
                // Save the loaner avatar
                String filename = storageService.store(avatar, UPLOAD_DIR, DEFAULT_IMAGE);

                // Set avatar filename
                loaner.setAvatar(filename);
            }

            // Update loaner
            loaner = loanerService.save(loaner);

            // Return loaner
            return ResponseEntity.ok(loaner);
        } catch (IOException e) {
            e.printStackTrace();
            // Return internal server error
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/upload-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LoanerVO> uploadLoanerAvatar(@RequestPart("id") UUID id,
                                                       @RequestPart("avatar") MultipartFile avatar) {
        try {
            // Get loaner by id
            LoanerVO loaner = loanerService.getById(id);

            // Check if loaner was found
            if (loaner == null) {
                // Return not found
                return ResponseEntity.notFound().build();
            }

            // Save the loaner avatar
            String filename = storageService.store(avatar, UPLOAD_DIR, DEFAULT_IMAGE);

            // Set avatar filename
            loaner.setAvatar(filename);

            // Update loaner
            loaner = loanerService.save(loaner);

            // Return loaner
            return ResponseEntity.ok(loaner);
        } catch (IOException e) {
            e.printStackTrace();
            // Return internal server error
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLoaner(@PathVariable UUID id) {
        // Get loaner by id
        LoanerVO loanerVo = loanerService.getById(id);

        // Check if loaner was found
        if (loanerVo == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Delete loaner
        loanerService.delete(id);

        // Return ok
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/images/{filename}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> getImage(@PathVariable String filename) {
        try {
            // Load image
            byte[] image = storageService.load(filename, UPLOAD_DIR);

            // Return image
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            e.printStackTrace();
            // Return not found
            return ResponseEntity.notFound().build();
        }
    }
}
