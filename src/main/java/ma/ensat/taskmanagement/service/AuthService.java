package ma.ensat.taskmanagement.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.ensat.taskmanagement.Enum.Role;
import ma.ensat.taskmanagement.Exception.EmailAlreadyExistsException;
import ma.ensat.taskmanagement.dto.LoginDTO;
import ma.ensat.taskmanagement.dto.RegisterDTO;
import ma.ensat.taskmanagement.entity.User;
import ma.ensat.taskmanagement.repository.UserRepository;
import ma.ensat.taskmanagement.security.JWTUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    public Map<String, String> login(LoginDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

        return Map.of(
                "token", token,
                "role", user.getRole().name()
        );
    }

    @Transactional
    public void register(RegisterDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        User user = User.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }
}
