package socialmediaapp.twitterinspiredapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialmediaapp.twitterinspiredapp.model.User;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowDto {

    private Long id;

    private User following;

    private User followed;

    private Timestamp followDate;

}
