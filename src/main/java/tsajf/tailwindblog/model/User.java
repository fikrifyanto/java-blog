package tsajf.tailwindblog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @NotNull(message = "Name is required!")
    @NotEmpty(message = "Name is required!")
    private String name;

    @NotNull(message = "Username is required!")
    @NotEmpty(message = "Username is required!")
    @Column(unique = true)
    private String username;

    @NotNull(message = "Password is required!")
    @NotEmpty(message = "Password is required!")
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ADMIN;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_path")
    private String imagePath;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        authorityList.add(authority);

        authority = new SimpleGrantedAuthority("SUPER_ADMIN");
        authorityList.add(authority);

        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isSuperAdmin() {
        return this.role == Role.SUPER_ADMIN;
    }

    public enum Role {
        ADMIN,
        SUPER_ADMIN
    }
}
