package socialmediaapp.twitterinspiredapp.dto;

import lombok.*;

@Builder
@Value
@AllArgsConstructor
public class PostRequest {
     String postName;
     String url;
     String description;
     String userName;
}

