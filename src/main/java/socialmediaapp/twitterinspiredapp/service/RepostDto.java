package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepostDto {

    private  Long id;

    private User user;

    private Post post;

}
