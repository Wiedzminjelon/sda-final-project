package socialmediaapp.twitterinspiredapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.CommentDto;
import socialmediaapp.twitterinspiredapp.service.CommentService;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@RequestBody socialmediaapp.twitterinspiredapp.dto.CommentDto commentDto) {
        return new ResponseEntity<>(commentService.save(commentDto), CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
       return ResponseEntity.ok()
        .body(commentService.getAllCommentsForPost(postId));

    }
    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String userName) {
        return ResponseEntity.ok()
                .body(commentService.getAllCommentForUser(userName));

    }

}
