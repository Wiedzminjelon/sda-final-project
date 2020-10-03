package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.PostsDto;
import socialmediaapp.twitterinspiredapp.exceptions.PostNotFoundException;

import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;




    public void save (PostsDto postsDto) {
        Post post = postRepository.findById(postsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(postsDto.getPostId().toString()));

    }

    public Stream<Post> getAllPostsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return postRepository.findByPost(post)
                .stream();
    }

    public Stream<Post> getAllPostsForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return  postRepository.findAllByUser(user)
                .stream();


    }
}