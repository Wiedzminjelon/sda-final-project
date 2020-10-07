package socialmediaapp.twitterinspiredapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long postId;
    private String text;
    private String userName;
}
