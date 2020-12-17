package socialmediaapp.twitterinspiredapp.service;

import socialmediaapp.twitterinspiredapp.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto save(PostDto postDto);

    List<PostDto> getAllPosts();

    List<PostDto> getAllPostsForUser(Long userId);

    PostDto getPost(Long postId);

}
