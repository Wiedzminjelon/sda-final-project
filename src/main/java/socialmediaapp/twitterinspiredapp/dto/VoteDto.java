package socialmediaapp.twitterinspiredapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialmediaapp.twitterinspiredapp.enums.VOTE_TYPE;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VOTE_TYPE vote_type;
    private Long postId;
    private Long userId;
    private Timestamp created;
}
