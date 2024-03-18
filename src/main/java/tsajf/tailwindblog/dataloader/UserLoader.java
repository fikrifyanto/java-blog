package tsajf.tailwindblog.dataloader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.UserRepository;

@Component
public class UserLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserLoader(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setName("admin");
            admin.setUsername("admin");
            admin.setRole(User.Role.ADMIN);

            String encodedPassword = passwordEncoder.encode("admin");
            admin.setPassword(encodedPassword);

            userRepository.save(admin);
        }
    }

}
