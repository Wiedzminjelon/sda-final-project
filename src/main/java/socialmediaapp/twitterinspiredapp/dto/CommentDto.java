package socialmediaapp.twitterinspiredapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialmediaapp.twitterinspiredapp.model.User;

import java.sql.Date;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long postId;
    private String text;
    private User user;
    private Timestamp created;
}



