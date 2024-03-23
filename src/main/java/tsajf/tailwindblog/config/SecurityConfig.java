package tsajf.tailwindblog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tsajf.tailwindblog.service.UserService;

@Configuration
public class SecurityConfig {

    private final UserService userService;


    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.formLogin(formLogin -> formLogin.loginPage("/login")
                        .defaultSuccessUrl("/admin/post"))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/", "/category/**", "/post/**", "/assets/css/*", "/assets/js/*", "/login").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                        .anyRequest().authenticated())
                .userDetailsService(userService)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}