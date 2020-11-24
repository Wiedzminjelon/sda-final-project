package socialmediaapp.twitterinspiredapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import socialmediaapp.twitterinspiredapp.dto.PostDto;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.service.PostService;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PostsControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createPost() {
    }


    @Test
    public void getAllPosts() throws Exception {

        when(postService.getAllPosts()).thenReturn(Arrays.asList(getPostDto(1), getPostDto(2)));

        mockMvc.perform(get("/api/posts/all"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].username", is("username")))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    public void getAllPostsForUser() {
    }

    @Test
    public void getPost() throws Exception {
        PostDto dto = getPostDto(1);

        when(postService.getPostById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/posts/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username", is("username")));

    }

    private PostDto getPostDto(long id) {
        PostDto dto = PostDto.builder()
                .user(new User())
                .voteCount(2)
                .numberOfComments(0)
                .id(id)
                .build();
        return dto;
    }
}
