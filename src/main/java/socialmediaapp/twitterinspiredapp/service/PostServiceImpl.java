package socialmediaapp.twitterinspiredapp.service;

import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.PostDto;

import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.exceptions.UserNotFoundException;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostDto save(PostDto postDto) {
        Post postToSave = fromPostDto(postDto);
        Post savedPost = postRepository.save(postToSave);
        return toPostDto(savedPost);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostServiceImpl::toPostDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> getAllPostsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        return postRepository.findAllByUser(user)
                .stream()
                .map(PostServiceImpl::toPostDto)
                .collect(Collectors.toList());
    }

    public PostDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new SpringTwitterException("Post Not Found"));
        return toPostDto(post);

    }


    static Post fromPostDto(PostDto postDto) {
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

    static PostDto toPostDto(Post post) {
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
