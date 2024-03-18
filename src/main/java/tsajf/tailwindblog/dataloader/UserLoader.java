package tsajf.tailwindblog.dataloader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.UserRepository;

@Component
public class UserLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setName("admin");
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
        }
    }

}
