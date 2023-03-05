package ma.emsi.solvefinity.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/all/names")
    @PreAuthorize("hasAnyAuthority('APP_ADMIN', 'BANK_ADMIN')")
    public ResponseEntity<?> getAllRolesNames() {
        return ResponseEntity.ok(roleService.getAllName());
    }
}
