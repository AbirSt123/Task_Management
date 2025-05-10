package ma.ensat.taskmanagement.config;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.ensat.taskmanagement.Enum.Role;
import ma.ensat.taskmanagement.entity.User;
import ma.ensat.taskmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository) {
        return args -> createAdminIfNotExists(userRepository);
    }

    @Transactional
    public void createAdminIfNotExists(UserRepository userRepository) {
        String adminEmail = "admin@example.com";

        if (userRepository.existsByEmail(adminEmail)) {
            System.out.println("Admin already exists.");
            return;
        }

        User admin = User.builder()
                .nom("Admin")
                .email(adminEmail)
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
        System.out.println("Admin user created.");
    }
}
