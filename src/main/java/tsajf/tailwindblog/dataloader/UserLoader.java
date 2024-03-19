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
        if (userRepository.findByUsername("superadmin") == null) {
            User admin = new User();
            admin.setName("superadmin");
            admin.setUsername("superadmin");
            admin.setRole(User.Role.SUPERADMIN);

            String encodedPassword = passwordEncoder.encode("superadmin");
            admin.setPassword(encodedPassword);

            userRepository.save(admin);
        }
    }

}
