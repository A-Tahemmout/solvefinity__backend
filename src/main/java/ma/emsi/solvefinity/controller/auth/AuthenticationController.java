package ma.emsi.solvefinity.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.payload.request.AuthenticationRequest;
import ma.emsi.solvefinity.payload.response.AuthenticationResponse;
import ma.emsi.solvefinity.service.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/sign-in-with-token")
    public ResponseEntity<AuthenticationResponse> loginWithToken(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.loginWithToken(request));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logout() {
        authenticationService.logout();

        return ResponseEntity.ok().build();
    }
}
