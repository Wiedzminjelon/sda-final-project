package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.PostDto;
import socialmediaapp.twitterinspiredapp.service.PostServiceImpl;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostServiceImpl postServiceImpl;

    public PostsController(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postServiceImpl.save(postDto),CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {

        return new ResponseEntity<>(postServiceImpl.getAllPosts(), OK);
    }

    @GetMapping("/all-by-user/{id}")
    public ResponseEntity<List<PostDto>> getAllPostsForUser(@PathVariable long id) {
        return new ResponseEntity<>(postServiceImpl.getAllPostsForUser(id), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postServiceImpl.getPost(id),OK);
    }




}
