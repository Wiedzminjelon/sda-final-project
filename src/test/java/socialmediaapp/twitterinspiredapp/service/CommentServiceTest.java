package socialmediaapp.twitterinspiredapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import socialmediaapp.twitterinspiredapp.dto.CommentDto;
import socialmediaapp.twitterinspiredapp.exceptions.PostNotFoundException;
import socialmediaapp.twitterinspiredapp.model.Comment;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.CommentRepository;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    CommentService commentService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    @Before
    public void init() {
        commentService = new CommentService(postRepository, userRepository, commentRepository);
    }

    @Test
    public void when_saveComment_then_returnOneCommentDto() {
        //given
        User testuser3 = new User();
        testuser3.setUserId(1L);

        Post post = Post.builder()
                .id(1L)
                .user(testuser3)
                .numberOfComments(0)
                .build();

        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .user(testuser3)
                .post(post)
                .text("comment")
                .build();
        //when
        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
        when(commentRepository.save(any())).thenReturn(CommentService.fromCommentDto(commentDto));
        CommentDto savedComment = commentService.save(commentDto);
        //then
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId() == 1L);
        assertThat(savedComment.getUser()).isEqualTo(testuser3);
        assertThat(savedComment.getText()).isEqualTo("comment");
        assertThat(savedComment.getPost()).isEqualTo(post);
        assertThat(post.getNumberOfComments() == 1);
    }


    @Test
    public void when_getAllCommentsForPost_then_returnEmptyList() {
        //given
        User testuser1 = createUserWithUsername("testuser1");
        Post postForUser = createPostForUser(testuser1, 1L);
        //when
        when(postRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(postForUser));
        when(commentRepository.findByPost(any())).thenReturn(new ArrayList<>());
        List<CommentDto> allCommentsForPost = commentService.getAllCommentsForPost(1L);
        //then
        assertThat(allCommentsForPost).isEmpty();
        assertThat(allCommentsForPost).isNotNull();
    }

    @Test
    public void when_getAllCommentsForPost_then_returnTwoComments() {
        //given
        User testuser1 = createUserWithUsername("testuser12");
        Post postForUser = createPostForUser(testuser1, 1L);
        Comment commentForPost1 = createCommentForPost(testuser1, postForUser, "first comment");
        Comment commentForPost2 = createCommentForPost(testuser1, postForUser, "second comment");
        //when
        when(postRepository.findById(2L)).thenReturn(java.util.Optional.ofNullable(postForUser));
        when(commentRepository.findByPost(postForUser)).thenReturn(Arrays.asList(commentForPost1, commentForPost2));
        List<CommentDto> allCommentsForPost = commentService.getAllCommentsForPost(2L);
        //then
        assertThat(allCommentsForPost).isNotEmpty();
        assertThat(allCommentsForPost).isNotNull();
        assertThat(allCommentsForPost.get(0) != null);
        assertThat(allCommentsForPost.get(1) != null);
        assertThat(allCommentsForPost.get(0).getText()).isEqualTo("first comment");
        assertThat(allCommentsForPost.get(1).getText()).isEqualTo("second comment");
    }

    @Test(expected = PostNotFoundException.class)
    public void when_getAllCommentsForPost_then_throwPostNotFoundException() {
        //given
        //when
        List<CommentDto> allCommentsForPost = commentService.getAllCommentsForPost(1L);
        //then
    }


    @Test
    public void when_getAllCommentForUser_then_returnEmptyList() {
        //given
        User user = new User();
        user.setUserId(1L);
        //when
        when(commentRepository.getAllByUser(user)).thenReturn(new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        List<CommentDto> allCommentForUsername = commentService.getAllCommentForUser(1L);
        //then
        assertThat(allCommentForUsername).isEmpty();
        assertThat(allCommentForUsername).isNotNull();
    }

    @Test
    public void when_getAllCommentForUser_then_returnTwoComments() {
        //given
        User testuser2 = createUserWithUsername("testuser2");
        testuser2.setUserId(1L);
        Post postForUser = createPostForUser(testuser2, 1);
        Comment someComment = createCommentForPost(testuser2, postForUser, "some comment");
        Comment anotherComment = createCommentForPost(testuser2, postForUser, "another comment");
        //when
        when(commentRepository.getAllByUser(testuser2)).thenReturn(Arrays.asList(someComment, anotherComment));
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testuser2));
        List<CommentDto> allCommentForUsername = commentService.getAllCommentForUser(testuser2.getUserId());
        //then
        assertThat(allCommentForUsername).isNotEmpty();
        assertThat(allCommentForUsername).isNotNull();
        assertThat(allCommentForUsername.get(0).getText()).isEqualTo("some comment");
        assertThat(allCommentForUsername.get(1).getText()).isEqualTo("another comment");
    }


    private Comment createCommentForPost(User user, Post post, String commentText) {
        return Comment.builder()
                .post(post)
                .user(user)
                .id(1L)
                .text(commentText)
                .build();
    }

    private Post createPostForUser(User user, long id) {
        return Post.builder()
                .postName("postname")
                .description("description")
                .user(user)
                .id(id)
                .build();
    }

    private User createUserWithUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }

}
