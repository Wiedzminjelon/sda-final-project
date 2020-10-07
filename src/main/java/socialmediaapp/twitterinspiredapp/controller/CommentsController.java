package socialmediaapp.twitterinspiredapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.CommentDto;
import socialmediaapp.twitterinspiredapp.dto.CommentResponse;
import socialmediaapp.twitterinspiredapp.service.CommentService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) {
        commentService.save(commentDto);
        return new ResponseEntity<>("Comment added!",CREATED);
    }
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForPost(@PathVariable Long postId) {
       return ResponseEntity.status(OK)
               .body(commentService.getAllCommentsForPost(postId));

    }
    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForUser(@PathVariable String userName) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentForUser(userName));

    }

}
