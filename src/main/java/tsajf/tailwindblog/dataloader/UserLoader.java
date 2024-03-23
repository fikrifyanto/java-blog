package tsajf.tailwindblog.dataloader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tsajf.tailwindblog.model.User;
import tsajf.tailwindblog.repository.UserRepository;

@Component
public class UserLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("superadmin") == null) {
            User admin = new User();
            admin.setName("Super Admin");
            admin.setUsername("superadmin");
            admin.setRole(User.Role.SUPER_ADMIN);

            String encodedPassword = passwordEncoder.encode("superadmin");
            admin.setPassword(encodedPassword);

            userRepository.save(admin);
        }
    }

}
