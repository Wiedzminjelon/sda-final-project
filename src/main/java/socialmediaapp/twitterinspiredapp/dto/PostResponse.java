package socialmediaapp.twitterinspiredapp.dto;


import lombok.*;

@Value
@Builder
@AllArgsConstructor
public class PostResponse {
     Long id;
     String postName;
     String url;
     String description;
     String userName;
}
