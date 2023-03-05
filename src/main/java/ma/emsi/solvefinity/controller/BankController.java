package ma.emsi.solvefinity.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.vo.BankVO;
import ma.emsi.solvefinity.service.BankService;
import ma.emsi.solvefinity.service.storage.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankController {
    private static final String UPLOAD_DIR = "images/banks/";

    private final BankService bankService;
    private final StorageService storageService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<List<BankVO>> getAllBanks() {
        return ResponseEntity.ok(bankService.getAll());
    }

    @GetMapping("/all/name")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<List<Object[]>> getAllBanksName() {
        return ResponseEntity.ok(bankService.getAllName());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<List<BankVO>> searchBanks(@RequestParam("search") String search) {
        return ResponseEntity.ok(bankService.search(search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<BankVO> getBankById(@PathVariable UUID id) {
        // Get bank by id
        BankVO bankVo = bankService.getById(id);

        // Check if bank was found
        if (bankVo == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Return bank
        return ResponseEntity.ok(bankVo);
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<BankVO> getBankBySlug(@PathVariable String slug) {
        // Get bank by slug
        BankVO bank = bankService.getBySlug(slug);

        // Check if bank was found
        if (bank == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Return bank
        return ResponseEntity.ok(bank);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<BankVO> createBank(@RequestPart("bank") BankVO bank,
                                             @RequestParam(value = "logo", required = false) MultipartFile logo) {
        try {
            // Check if bank is being updated with a new logo
            if (logo != null) {
                // Store the bank logo
                String filename = storageService.store(logo, UPLOAD_DIR, "bank-icon.png");

                // Set logo filename
                bank.setLogo(filename);
            }

            // Save bank
            bank = bankService.save(bank);

            // Return bank
            return ResponseEntity.ok(bank);
        } catch (IOException e) {
            e.printStackTrace();
            // Return bad request
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<?> updateBank(@RequestPart("bank") BankVO bank, @RequestParam(value = "logo", required = false) MultipartFile logo) {
        try {
            // Check if bank is being updated with a new logo
            if (logo != null) {
                // Store the bank logo
                String filename = storageService.store(logo, UPLOAD_DIR, "bank-icon.png");

                // Set logo filename
                bank.setLogo(filename);
            }

            // Get bank by id
            BankVO bankExists = bankService.getById(bank.getId());

            // Check if bank exists
            if (bankExists == null) {
                return ResponseEntity.notFound().build();
            }

            bank.setVersion(bankExists.getVersion());
            bank.setCreatedAt(bankExists.getCreatedAt());
            bank.setUpdatedAt(bankExists.getUpdatedAt());

            // Save bank
            bank = bankService.save(bank);

            // Return bank
            return ResponseEntity.ok(bank);
        } catch (IOException e) {
            e.printStackTrace();
            // Return bad request
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<?> deleteBank(@PathVariable UUID id) {
        BankVO bank = bankService.getById(id);

        if (bank == null) {
            return ResponseEntity.notFound().build();
        }

        bankService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagination/all")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllBanks(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "asc") String sortDirection,
                                                           @RequestParam(defaultValue = "name") String sortBy) {
        // Get all banks
        Page<BankVO> banks = bankService.getAll(page, size, sortDirection, sortBy);

        return banksPaginationResponse(banks);
    }

    @GetMapping("/pagination/search")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<Map<String, Object>> searchBanks(@RequestParam("search") String search,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "asc") String sortDirection,
                                                           @RequestParam(defaultValue = "name") String sortBy) {
        // Get all banks by name
        Page<BankVO> banks = bankService.search(search, page, size, sortDirection, sortBy);

        return banksPaginationResponse(banks);
    }

    private ResponseEntity<Map<String, Object>> banksPaginationResponse(Page<BankVO> banks) {
        Map<String, Object> response = new HashMap<>();

        response.put("banks", banks.getContent());
        response.put("currentPage", banks.getNumber());
        response.put("pageSize", banks.getSize());
        response.put("totalItems", banks.getTotalElements());
        response.put("totalPages", banks.getTotalPages());

        return ResponseEntity.ok(response);
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
