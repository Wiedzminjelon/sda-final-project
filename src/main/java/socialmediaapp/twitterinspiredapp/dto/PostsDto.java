package socialmediaapp.twitterinspiredapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsDto {
    private String postName;
    private String url;
    private String description;
    private String userName;
}
