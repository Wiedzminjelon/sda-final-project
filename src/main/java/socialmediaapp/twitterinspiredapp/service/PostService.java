package socialmediaapp.twitterinspiredapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import socialmediaapp.twitterinspiredapp.dto.PostDto;

import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public PostService(PostRepository postRepository, UserRepository userRepository, AuthService authService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Transactional
    public PostDto save(PostDto postDto) {
        userRepository.findByUsername(postDto.getUserName()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.OK, "User not found!"));
        Post post = mapPostDtoToPost(postDto);
        postRepository.save(post);
        return postDto;
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostService::mapPostToPostDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getAllPostsForUser(String username) {
        return postRepository.findAllByUser_Username(username)
                .stream()
                .map(PostService::mapPostToPostDto)
                .collect(Collectors.toList());
    }


    private Post mapPostDtoToPost(PostDto postDto) {
        return Post.builder()
                .postName(postDto.getPostName())
                .description(postDto.getDescription())
                .voteCount(0)
                .createdDate(Instant.now())
                .url(postDto.getUrl())
                .user(userRepository.findByUsername(postDto.getUserName())
                        .orElseThrow(() -> new SpringTwitterException("User not found!")))
                .build();
    }

    static PostDto mapPostToPostDto(Post post) {
        return PostDto.builder()
                .userName(post.getUser().getUsername())
                .url(post.getUrl())
                .description(post.getDescription())
                .postName(post.getPostName())
                .id(post.getPostId())
                .build();
    }

}
