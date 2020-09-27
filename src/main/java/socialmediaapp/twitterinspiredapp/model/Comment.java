package socialmediaapp.twitterinspiredapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotEmpty
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
