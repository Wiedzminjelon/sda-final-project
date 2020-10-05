package socialmediaapp.twitterinspiredapp.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Value
@AllArgsConstructor
public class PostRequest {

    @NotBlank
    String postName;

    String url;

    @NotBlank
    String description;

    @NotBlank
    String userName;
}

