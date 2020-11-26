package socialmediaapp.twitterinspiredapp.service;

import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.CommentDto;
import socialmediaapp.twitterinspiredapp.exceptions.PostNotFoundException;

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
        Post post = postRepository.findById(commentDto.getPost().getId())
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));
        Comment savedComment = commentRepository.save(fromCommentDto(commentDto));

        post.setNumberOfComments(post.getNumberOfComments() + 1);
        Post savedPost = postRepository.save(post);
        return toCommentDto(savedComment);
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not Found!"));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentForUser(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found!"));
        return commentRepository.getAllByUser(user)
                .stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }

    static Comment fromCommentDto(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .user(commentDto.getUser())
                .post(commentDto.getPost())
                .build();
    }

    private CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .post(comment.getPost())
                .text(comment.getText())
                .user(comment.getUser())
                .created(comment.getCreated())
                .build();
    }
}
