package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.CommentDto;
import socialmediaapp.twitterinspiredapp.service.CommentService;
import socialmediaapp.twitterinspiredapp.service.CommentServiceImpl;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.save(commentDto), CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(commentService.getAllCommentsForPost(postId));

    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(commentService.getAllCommentsForUser(id));

    }

}
