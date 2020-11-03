package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.PostDto;
import socialmediaapp.twitterinspiredapp.service.PostService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.save(postDto),CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {

        return new ResponseEntity<>(postService.getAllPosts(), OK);
    }

    @GetMapping("/all-by-user/{username}")
    public ResponseEntity<List<PostDto>> getAllPostsForUser(@PathVariable String username) {
        return new ResponseEntity<>(postService.getAllPostsForUser(username), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPostById(id),OK);
    }




}
