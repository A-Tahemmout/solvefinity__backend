package ma.emsi.solvefinity.service.auth;

import lombok.RequiredArgsConstructor;
import ma.emsi.solvefinity.domain.converter.UserConverter;
import ma.emsi.solvefinity.domain.vo.UserVO;
import ma.emsi.solvefinity.model.Role;
import ma.emsi.solvefinity.model.User;
import ma.emsi.solvefinity.payload.request.AuthenticationRequest;
import ma.emsi.solvefinity.payload.response.AuthenticationResponse;
import ma.emsi.solvefinity.repository.UserRepository;
import ma.emsi.solvefinity.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user from authentication
        var user = (User) authentication.getPrincipal();

        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse loginWithToken(AuthenticationRequest request) {
        // Get access token from request
        var accessToken = request.getAccessToken();

        // Get username from token
        var username = jwtUtils.extractUsername(accessToken);

        // Get user from database
        var user = userRepository.findByUsername(username).orElseThrow();

        // Token Claims
        return getAuthenticationResponse(user);
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        // Token Claims
        Map<String, Object> claims = Map.of(
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "roles", user.getRoles().stream().map(Role::getName).toArray()
        );

        // Generate token
        var token = jwtUtils.generateToken(claims, user);

        // Return response
        return new AuthenticationResponse().builder()
                .uuid(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .accessToken(token)
                .build();
    }

    public UserVO getCurrentUser() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return UserConverter.toVO(user);
    }

    public void logout() {
        // Clear security context
        SecurityContextHolder.clearContext();
    }
}
