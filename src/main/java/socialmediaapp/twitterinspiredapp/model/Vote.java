package socialmediaapp.twitterinspiredapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import socialmediaapp.twitterinspiredapp.enums.VOTE_TYPE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    private VOTE_TYPE vote_type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", columnDefinition = "userId")
    private User user;


}
