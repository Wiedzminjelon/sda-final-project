package socialmediaapp.twitterinspiredapp.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor
public class PostDto {

     Long id;

     @NotBlank
     String postName;


     String url;

     @NotBlank
     String description;

     @NotBlank
     String userName;
}
