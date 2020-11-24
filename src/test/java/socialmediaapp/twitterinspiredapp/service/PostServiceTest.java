package socialmediaapp.twitterinspiredapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import socialmediaapp.twitterinspiredapp.dto.PostDto;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotSame;
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
    public void init() {
        postService = new PostService(postRepository, userRepository);
    }

    @Test
    public void when_getAllPosts_then_returnEmptyList() {
        //when
        when(postRepository.findAll()).thenReturn(new ArrayList<>());
        //then
        assertThat(postService.getAllPosts()).isNotNull();
        assertThat(postService.getAllPosts()).isEmpty();
    }

    @Test
    public void when_getAllPosts_then_returnOnePostDto() {
        //when
        when(postRepository.findAll()).thenReturn(Collections.singletonList(new Post()));
        List<PostDto> posts = postService.getAllPosts();
        //then
        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0)).isEqualTo(new PostDto());
    }

    @Test
    public void when_getAllPosts_then_returnTwoPostDto() {
        //given
        Post post = Post.builder()
                .id(0L)
                .description("Post 1 description")
                .build();
        Post post2 = Post.builder()
                .id(1L)
                .description("Post 2 description")
                .build();
        //when
        when(postRepository.findAll()).thenReturn(Arrays.asList(post, post2));
        List<PostDto> posts = postService.getAllPosts();
        //then
        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0).getId()).isNotNull();
        assertThat(posts.get(1).getId()).isNotNull();
        assertNotSame(posts.get(0).getId(), (posts.get(1).getId()));
    }


    @Test
    public void when_getAllPostsForUsername_then_returnOnePost() {
        //given
        User user = createUserWithUsername("username");

        Post post = Post.builder()
                .id(1L)
                .postName("postname")
                .user(user)
                .build();
        //when
        when(postRepository.findAllByUser_Username(any())).thenReturn(Collections.singletonList(post));
        List<PostDto> posts = postService.getAllPostsForUsername("username");
        //then
        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0).getUser().getUsername()).isEqualTo("username");
        assertThat(posts.get(0).getPostName()).isEqualTo("postname");
        assertThat(posts.get(0).getId() == 1L);
    }

    @Test
    public void when_getAllPostsForUsername_then_returnPostsForUser() {
        //given
        User user1 = createUserWithUsername("user1");
        User user2 = createUserWithUsername("user2");

        Post post = createPostForUser(user1);
        Post post2 = createPostForUser(user1);
        Post post3 = createPostForUser(user2);
        //when
        when(postRepository.findAllByUser_Username(user1.getUsername())).thenReturn(Arrays.asList(post, post2));
        when(postRepository.findAllByUser_Username(user2.getUsername())).thenReturn(Collections.singletonList(post3));
        List<PostDto> postsForUser1 = postService.getAllPostsForUsername("user1");
        List<PostDto> postsForUser2 = postService.getAllPostsForUsername("user2");
        //then
        assertThat(postsForUser1).isNotNull();
        assertThat(postsForUser2).isNotNull();
        assertThat(postsForUser1).isNotEmpty();
        assertThat(postsForUser2).isNotEmpty();
        assertThat(postsForUser1.get(0).getUser().getUsername()).isEqualTo("user1");
        assertThat(postsForUser1.get(1).getUser().getUsername()).isEqualTo("user1");
        assertThat(postsForUser2.get(0).getUser().getUsername()).isEqualTo("user2");
        assertThat(postsForUser1 != postsForUser2);
    }


    @Test
    public void when_save_then_returnPostDto() {
        //given
        User user1 = createUserWithUsername("user1");
        PostDto postDto = PostDto.builder()
                .postName("postname")
                .user(user1)
                .description("description")
                .url("")
                .build();
        //when
        when(postRepository.save(any())).thenReturn(PostService.mapPostDtoToPost(postDto));
        PostDto savedPost = postService.save(postDto);

        //then
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getPostName()).isEqualTo("postname");
        assertThat(savedPost.getDescription()).isEqualTo("description");
        assertThat(savedPost.getUser().getUsername()).isEqualTo("user1");
        assertThat(savedPost.getUrl()).isEqualTo("");
    }

    @Test
    public void when_getPostById_then_returnOnePost() {
        //given
        User user1 = createUserWithUsername("user1");
        Post post1 = createPostForUser(user1);
        post1.setId(1L);
        //when
        when(postRepository.findById(1L)).thenReturn(Optional.of(post1));
        PostDto foundPost = postService.getPostById(1L);
        //then
        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getId() == 1L);
        assertThat(foundPost.getUser().getUsername()).isEqualTo("user1");
        assertThat(foundPost.getDescription()).isEqualTo("description");
    }

    @Test(expected = RuntimeException.class)
    public void when_getPostById_then_returnThrowsRuntimeException() {
        //given
        //when
        PostDto foundPost = postService.getPostById(1L);
        //then
    }








    private Post createPostForUser(User user) {
        return Post.builder()
                .user(user)
                .description("description")
                .build();
    }

    private User createUserWithUsername(String user) {
        User user1 = new User();
        user1.setUsername(user);
        return user1;
    }



}
