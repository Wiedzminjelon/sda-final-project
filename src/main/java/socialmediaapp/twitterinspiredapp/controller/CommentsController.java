package socialmediaapp.twitterinspiredapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.CommentsDto;
import socialmediaapp.twitterinspiredapp.model.Comment;
import socialmediaapp.twitterinspiredapp.service.CommentService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.stream.Stream;

@RestController
@RequestMapping(" /api/comments/")
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }
    @GetMapping(" /by-user/{postId}")
    public ResponseEntity<Stream<Comment>> getAllCommentsforPost(@PathVariable Long postId) {
       return ResponseEntity.status(OK)
               .body(commentService.getAllCommentsForPost(postId));

    }
    @GetMapping("/by-user/{userName}")
    public ResponseEntity<Stream<Comment>> getAllCommentForUser(@PathVariable String userName) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentForUser(userName));

    }

}
