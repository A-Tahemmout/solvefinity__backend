package ma.emsi.solvefinity.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.vo.BankVO;
import ma.emsi.solvefinity.domain.vo.UserVO;
import ma.emsi.solvefinity.service.BankService;
import ma.emsi.solvefinity.service.RoleService;
import ma.emsi.solvefinity.service.UserService;
import ma.emsi.solvefinity.service.auth.AuthenticationService;
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
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private static final String UPLOAD_DIR = "images/users/";

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final RoleService roleService;
    private final BankService bankService;
    private final StorageService storageService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        // Get user by id
        UserVO userVo = userService.getById(id);

        // Check if user was found
        if (userVo == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Return user
        return ResponseEntity.ok(userVo);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<List<UserVO>> searchUsers(@RequestParam("search") String search) {
        return ResponseEntity.ok(userService.search(search));
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<?> createUser(@RequestPart("user") UserVO user,
                                        @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
        try {
            // Check if avatar is not null
            if (avatar != null) {
                // Store the user avatar
                String filename = storageService.store(avatar, UPLOAD_DIR, "loaner-icon.png");

                // Set avatar filename
                user.setAvatar(filename);
            }

            // Get current user
            UserVO currentUser = authenticationService.getCurrentUser();

            // Set user bank
            user.setBank(currentUser.getBank());

            // Set user role
            user.setRoles(roleService.getByNames(user.getRoles()));

            // Save user
            user = userService.save(user);

            // Return user
            return ResponseEntity.ok(user);
        } catch (IOException e) {
            e.printStackTrace();
            // Return bad request
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/update", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestPart("user") UserVO user, @RequestParam(value = "logo", required = false) MultipartFile logo) {
        try {
            // Check if user is being updated with a new logo
            if (logo != null) {
                // Store the user logo
                String filename = storageService.store(logo, UPLOAD_DIR, "loaner-icon.png");

                // Set avatar filename
                user.setAvatar(filename);
            }

            // Check if user has a bank
            if (user.getBank() != null) {
                // Get bank by id
                BankVO bank = bankService.getBySlug(user.getBank().getSlug());

                // Check if bank exists
                if (bank == null) {
                    return ResponseEntity.notFound().build();
                }

                // Set bank
                user.setBank(bank);
            }

            // Check if user has a role
            if (user.getRoles() != null) {
                // Get role by id
                user.setRoles(roleService.getByNames(user.getRoles()));
            } else {
                // Set default role
                user.setRoles(null);
            }

            // Get user by id
            UserVO userExists = userService.getById(user.getId());

            // Check if user exists
            if (userExists == null) {
                return ResponseEntity.notFound().build();
            }

            user.setPassword(userExists.getPassword());

            user.setVersion(userExists.getVersion());
            user.setCreatedAt(userExists.getCreatedAt());
            user.setUpdatedAt(userExists.getUpdatedAt());

            // Save user
            user = userService.save(user);

            // Return user
            return ResponseEntity.ok(user);
        } catch (IOException e) {
            e.printStackTrace();
            // Return bad request
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        // Get user by id
        UserVO user = userService.getById(id);

        // Check if user was found
        if (user == null) {
            // Return not found
            return ResponseEntity.notFound().build();
        }

        // Delete user
        userService.delete(id);

        // Return ok
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagination/all")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "asc") String sortDirection,
                                                           @RequestParam(defaultValue = "name") String sortBy) {
        // Get all users
        Page<UserVO> users = userService.getAll(page, size, sortDirection, sortBy);

        return usersPaginationResponse(users);
    }

    @GetMapping("/pagination/search")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<Map<String, Object>> searchUsers(@RequestParam("search") String search,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "asc") String sortDirection,
                                                           @RequestParam(defaultValue = "name") String sortBy) {
        // Get all users by name
        Page<UserVO> users = userService.search(search, page, size, sortDirection, sortBy);

        return usersPaginationResponse(users);
    }

    private ResponseEntity<Map<String, Object>> usersPaginationResponse(Page<UserVO> users) {
        Map<String, Object> response = new HashMap<>();

        response.put("users", users.getContent());
        response.put("currentPage", users.getNumber());
        response.put("pageSize", users.getSize());
        response.put("totalItems", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

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
