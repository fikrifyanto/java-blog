package tsajf.tailwindblog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public enum Role {
        ADMIN,
        SUPER_ADMIN
    }
}
