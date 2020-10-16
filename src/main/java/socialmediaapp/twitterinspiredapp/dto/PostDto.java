package socialmediaapp.twitterinspiredapp.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostDto {

     private Long id;

     @NotBlank
     private String postName;


    private String url;

     @NotBlank
     private String description;

     @NotBlank
     private String userName;
}
