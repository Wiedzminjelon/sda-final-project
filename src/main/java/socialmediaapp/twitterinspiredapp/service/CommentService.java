package socialmediaapp.twitterinspiredapp.service;

import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.CommentDto;
import socialmediaapp.twitterinspiredapp.exceptions.PostNotFoundException;

import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.Comment;
import socialmediaapp.twitterinspiredapp.model.Post;
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
    private final AuthService authService;
    private final CommentRepository commentRepository;

    public CommentService(PostRepository postRepository, UserRepository userRepository, AuthService authService, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authService = authService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentDto save(CommentDto commentDto) {
        postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));
        Comment comment = mapCommentDtoToComment(commentDto);
        commentRepository.save(comment);
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

    public List<CommentDto> getAllCommentForUser(String username) {
        return commentRepository.getAllByUser_Username(username)
                .stream()
                .map(this::mapCommentToCommentDto)
                .collect(Collectors.toList());
    }

    private Comment mapCommentDtoToComment(CommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .user(userRepository.findByUsername(commentDto.getUsername()).orElseThrow(() -> new SpringTwitterException("User not found!")))
                .post(postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new SpringTwitterException("Post not found!")))
                .build();
    }

    private CommentDto mapCommentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .text(comment.getText())
                .username(comment.getUser().getUsername())
                .created(comment.getCreated())
                .build();
    }
}
