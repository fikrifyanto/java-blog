package tsajf.tailwindblog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @NotNull(message = "Category is required!")
    private Category category;

    @OneToOne
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private Media media;

    @NotNull(message = "Title is required!")
    @NotEmpty(message = "Title is required!")
    private String title;

    @NotNull(message = "Date is required!")
    @NotEmpty(message = "Date is required!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @NotNull(message = "Content is required!")
    @NotEmpty(message = "Content is required!")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
