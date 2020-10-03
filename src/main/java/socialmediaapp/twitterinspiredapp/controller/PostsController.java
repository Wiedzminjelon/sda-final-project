package socialmediaapp.twitterinspiredapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import socialmediaapp.twitterinspiredapp.dto.PostsDto;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.service.PostService;

import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(" /api/posts/")
@AllArgsConstructor
public class PostsController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostsDto postsDto) {
        postService.save(postsDto);
        return new ResponseEntity<>(CREATED);
    }
    @GetMapping(" /by-post/{postId}")
    public ResponseEntity<Stream<Post>> getAllPostsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(postService.getAllPostsForPost(postId));

    }
    @GetMapping("/by-user/{userName}")
    public ResponseEntity<Stream<Post>> getAllPostsForUser(@PathVariable String userName) {
        return ResponseEntity.status(OK)
                .body(postService.getAllPostsForUser(userName));

    }
}
