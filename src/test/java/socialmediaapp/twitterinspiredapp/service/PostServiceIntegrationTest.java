package socialmediaapp.twitterinspiredapp.service;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import socialmediaapp.twitterinspiredapp.dto.PostDto;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostServiceIntegrationTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;


    @Test
    public void dependencyMustBeInjected() {
        assertThat(postService).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void whenPostSaveThenreturnPost() {
        User user = saveUser("marek");
        PostDto dto = createPostDto(user);

        PostDto post = postService.save(dto);

        assertThat(post).isNotNull();
        assertThat(post.getDescription()).isEqualTo("description");
        assertThat(post.getPostName()).isEqualTo("name");
        assertThat(post.getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void whenFindAllPostsThenReturnTwoPosts() {

        User user = saveUser("marcin");
        PostDto postDto = createPostDto(user);

        PostDto post = postService.save(postDto);
        PostDto post2 = postService.save(postDto);

        List<PostDto> allPosts = postService.getAllPosts();

        assertThat(allPosts).isNotEmpty();
        assertThat(allPosts.get(0)).isNotNull();
        assertThat(allPosts.get(1)).isNotNull();
        assertThat(allPosts.size()).isEqualTo(2);
        assertNotSame(allPosts.get(0).getId(), allPosts.get(1).getId());

    }

    private PostDto createPostDto(User user) {
        return PostDto.builder()
                .postName("name")
                .description("description")
                .user(user)
                .build();
    }

    private User saveUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmail("marcin@gmail.com");
        user.setPassword("password");
        userRepository.save(user);
        return user;
    }

}
