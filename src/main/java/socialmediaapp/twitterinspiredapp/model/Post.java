package socialmediaapp.twitterinspiredapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "Post name cannot be empty")
    private String postName;

    @Nullable
    private String url;

    @Nullable
    private String description;

    private Integer voteCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Instant createDate;
}
