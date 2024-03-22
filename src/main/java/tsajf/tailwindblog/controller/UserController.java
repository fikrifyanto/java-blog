package tsajf.tailwindblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tsajf.tailwindblog.entity.User;
import tsajf.tailwindblog.repository.UserRepository;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/user")
    public String index(Model model) {
        model.addAttribute("title", "User List");
        model.addAttribute("page", "admin/user/index");
        model.addAttribute("users", userRepository.findAll());
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Add User");
        model.addAttribute("page", "admin/user/create");
        return "admin/fragments/layout";
    }

    @PostMapping("/admin/user/store")
    public String create(@ModelAttribute User store) {
        store.setRole(User.Role.ADMIN);
        userRepository.save(store);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("title", "Edit User");
        model.addAttribute("page", "admin/user/edit");
        model.addAttribute("user", user);
        return "admin/fragments/layout";
    }

    @PostMapping("/admin/user/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute User update) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setName(update.getName());
        user.setUsername(update.getUsername());
        user.setPassword(update.getPassword());
        userRepository.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/admin/user";
    }

}
