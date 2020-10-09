package socialmediaapp.twitterinspiredapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialmediaapp.twitterinspiredapp.enums.VOTE_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    private VOTE_TYPE vote_type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Instant createDate;

}
