package socialmediaapp.twitterinspiredapp.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor
public class PostResponse {

     Long id;

     @NotBlank
     String postName;


     String url;

     @NotBlank
     String description;

     @NotBlank
     String userName;
}
