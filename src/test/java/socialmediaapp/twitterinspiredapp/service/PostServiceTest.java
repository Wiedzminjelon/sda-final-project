package socialmediaapp.twitterinspiredapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import socialmediaapp.twitterinspiredapp.dto.PostDto;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {


    PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init(){
        postService = new PostService(postRepository, userRepository);
    }

    @org.junit.Test
    public void whenGetAllPostsThenReturnEmptyList() {
        when(postRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(postService.getAllPosts()).isNotNull();
        assertThat(postService.getAllPosts()).isEmpty();
    }

    @org.junit.Test
    public void whenGetAllPostsThenReturnOnePost() {
        when(postRepository.findAll()).thenReturn(Arrays.asList(new Post()));

        List<PostDto> posts = postService.getAllPosts();
        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0)).isEqualTo(new PostDto());
    }


    @Test
    public void whenGetAllPostsForUserThenReturnPosts() {
        when(postRepository.findAllByUser_Username(any())).thenReturn(Arrays.asList(new Post()));

        List<PostDto> posts = postService.getAllPostsForUsername("username");
        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0)).isEqualTo(new PostDto());
    }

    @Test
    public void whenGetAllPostsForUserThenReturnPostWithDescription() {
        Post post = new Post();
        post.setDescription("Example description");
        when(postRepository.findAllByUser_Username(any())).thenReturn(Arrays.asList(post));

        List<PostDto> posts = postService.getAllPostsForUsername("username");
        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0)).isEqualTo(PostDto.builder().description("Example description").build());
    }


}
