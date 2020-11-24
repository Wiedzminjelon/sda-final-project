package socialmediaapp.twitterinspiredapp.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.CommentDto;
import socialmediaapp.twitterinspiredapp.exceptions.PostNotFoundException;

import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.exceptions.UserNotFoundException;
import socialmediaapp.twitterinspiredapp.model.Comment;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.CommentRepository;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentDto save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));
        Comment comment = mapCommentDtoToComment(commentDto);
        commentRepository.save(comment);
        post.setNumberOfComments(post.getNumberOfComments() + 1);
        postRepository.save(post);
        return mapCommentToCommentDto(comment);
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not Found!"));
        return commentRepository.findByPost_Id(postId)
                .stream()
                .map(this::mapCommentToCommentDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentForUser(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found!"));
        return commentRepository.getAllByUser(user)
                .stream()
                .map(this::mapCommentToCommentDto)
                .collect(Collectors.toList());
    }

    private Comment mapCommentDtoToComment(CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .user(userRepository.findByUsername(commentDto.getUser().getUsername()).orElseThrow(() -> new SpringTwitterException("User not found!")))
                .post(postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new SpringTwitterException("Post not found!")))
                .build();
    }

    private CommentDto mapCommentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .text(comment.getText())
                .user(comment.getUser())
                .created(comment.getCreated())
                .build();
    }
}
