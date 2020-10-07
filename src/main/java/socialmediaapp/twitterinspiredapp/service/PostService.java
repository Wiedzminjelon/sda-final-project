package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import socialmediaapp.twitterinspiredapp.dto.PostRequest;

import socialmediaapp.twitterinspiredapp.dto.PostResponse;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public void save(PostRequest postRequest) {
        String userName = postRequest.getUserName();
        userRepository.findByUsername(userName).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!"));
        postRepository.save(mapPostRequestToPost(postRequest));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostService::mapPostToPostResponse)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getAllPostsForUser(String username) {
        return postRepository.findAllByUser_Username(username)
                .stream()
                .map(PostService::mapPostToPostResponse)
                .collect(Collectors.toList());
    }


    private Post mapPostRequestToPost(PostRequest postRequest) {
        return Post.builder()
                .postName(postRequest.getPostName())
                .description(postRequest.getDescription())
                .voteCount(0)
                .createdDate(Instant.now())
                .url(postRequest.getUrl())
                .user(userRepository.findByUsername(postRequest.getUserName()).orElse(new User()))
                .build();
    }

    static PostResponse mapPostToPostResponse(Post post) {
        return PostResponse.builder()
                .userName(post.getUser().getUsername())
                .url(post.getUrl())
                .description(post.getDescription())
                .postName(post.getPostName())
                .id(post.getPostId())
                .build();
    }

}
