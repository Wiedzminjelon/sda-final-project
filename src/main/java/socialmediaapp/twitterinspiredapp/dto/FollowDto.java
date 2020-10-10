package socialmediaapp.twitterinspiredapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowDto {

    private Long id;

    private String followingUserName;

    private String followedUserName;

}
