package ma.ensat.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.ensat.taskmanagement.dto.AuthResponse;
import ma.ensat.taskmanagement.dto.LoginDTO;
import ma.ensat.taskmanagement.dto.RegisterDTO;
import ma.ensat.taskmanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and return JWT token with role")
    public ResponseEntity<AuthResponse> login(@Validated @RequestBody LoginDTO request) {
        Map<String, String> authResult = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(
                "Login successful",
                authResult.get("token"),
                authResult.get("role")
        ));
    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Register a new user account")
    public ResponseEntity<AuthResponse> register(@Validated @RequestBody RegisterDTO request) {
        authService.register(request);
        return ResponseEntity.ok(new AuthResponse("User registered successfully"));
    }
}
