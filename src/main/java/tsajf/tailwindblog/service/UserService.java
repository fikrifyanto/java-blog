package tsajf.tailwindblog.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.model.UserModel;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private static final List<UserModel> users = new ArrayList<>();

    private final PasswordEncoder passwordEncoder;

    public void register(UserModel user) {
        user.setName("oke");
        user.setRole(String.valueOf(User.Role.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.add(user);
        System.out.println(users);
    }

    public UserModel findByLogin(String login) {
        return users.stream().filter(user -> user.getUsername().equals(login))
                .findFirst()
                .orElse(null);
    }

}
