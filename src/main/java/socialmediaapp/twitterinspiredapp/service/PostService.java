package socialmediaapp.twitterinspiredapp.service;

import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.PostDto;

import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostDto save(PostDto postDto) {
        Post postToSave = mapPostDtoToPost(postDto);
        Post savedPost = postRepository.save(postToSave);
        return mapPostToPostDto(savedPost);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostService::mapPostToPostDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getAllPostsForUsername(String username) {
        return postRepository.findAllByUser_Username(username)
                .stream()
                .map(PostService::mapPostToPostDto)
                .collect(Collectors.toList());
    }

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new SpringTwitterException("Post Not Found"));
        return mapPostToPostDto(post);

    }


    static Post mapPostDtoToPost(PostDto postDto) {
        return Post.builder()
                .postName(postDto.getPostName())
                .description(postDto.getDescription())
                .voteCount(0)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .url(postDto.getUrl())
                .user(postDto.getUser())
                .numberOfComments(0)
                .build();
    }

    static PostDto mapPostToPostDto(Post post) {
        PostDto.PostDtoBuilder postDtoBuilder = PostDto.builder()
                .url(post.getUrl())
                .description(post.getDescription())
                .postName(post.getPostName())
                .id(post.getId())
                .voteCount(post.getVoteCount())
                .created(post.getCreated())
                .numberOfComments(post.getNumberOfComments());
        if (post.getUser() != null){
            postDtoBuilder.user(post.getUser());
        }
        return postDtoBuilder.build();
    }

}
