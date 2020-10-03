package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.CommentsDto;
import socialmediaapp.twitterinspiredapp.exceptions.PostNotFoundException;

import socialmediaapp.twitterinspiredapp.model.Comment;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.CommentRepository;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;

    

    public void save (CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return (List<CommentsDto>) commentRepository.findByPost(post)
                .stream();
    }

    public List<CommentsDto> getAllCommentForUser(String userName) {
         User user = userRepository.findByUsername(userName);
         return (List<CommentsDto>) commentRepository.findAllByUser(user)
                 .stream();


    }
}
