package socialmediaapp.twitterinspiredapp.dto;


import lombok.*;
import socialmediaapp.twitterinspiredapp.model.User;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank
    private String postName;

    private String url;

    @NotBlank
    private String description;

    private Integer voteCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Timestamp created;

    private Integer numberOfComments;
}
