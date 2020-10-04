package socialmediaapp.twitterinspiredapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.PostRequest;
import socialmediaapp.twitterinspiredapp.dto.PostResponse;
import socialmediaapp.twitterinspiredapp.service.PostService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostsController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity("Post added successfully!", CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), OK);
    }

    @GetMapping("/all-by-user/{username}")
    public ResponseEntity<List<PostResponse>> getAllPostsForUser(@PathVariable String username) {
        return new ResponseEntity<>(postService.getAllPostsForUser(username), OK);
    }

}
