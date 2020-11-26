package socialmediaapp.twitterinspiredapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import socialmediaapp.twitterinspiredapp.dto.FollowDto;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.exceptions.UserNotFoundException;
import socialmediaapp.twitterinspiredapp.model.Follow;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.FollowRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FollowServiceTest {

    FollowService followService;

    @Mock
    UserRepository userRepository;
    @Mock
    FollowRepository followRepository;

    @Before
    public void init() {
        followService = new FollowService(followRepository, userRepository);
    }

    @Test
    public void follow() {
    }

    @Test
    public void when_getAllFollowers_then_returnEmptyList() {
        //given
        User user = new User();
        user.setUserId(1L);
        //when
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(followRepository.findAllByFollowed_UserId(user.getUserId())).thenReturn(new ArrayList<>());
        List<FollowDto> allFollowersForUser = followService.getAllFollowers(1L);
        //then
        assertThat(allFollowersForUser.isEmpty());
        assertThat(allFollowersForUser).isNotNull();
    }

    @Test
    public void when_getAllFollowers_then_returnOneFollow() {
        //given
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);

        Follow follow = Follow.builder()
                .id(1L)
                .followed(user1)
                .following(user2)
                .build();
        //when
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user1));
        when(followRepository.findAllByFollowed_UserId(user1.getUserId())).
                thenReturn(Collections.singletonList(follow));
        List<FollowDto> allFollowersForUser = followService.getAllFollowers(1L);
        //then
        assertThat(allFollowersForUser.isEmpty());
        assertThat(allFollowersForUser).isNotNull();
        assertThat(allFollowersForUser.get(0) != null);
        assertThat(allFollowersForUser.get(0).getId() == 1);
        assertThat(allFollowersForUser.get(0).getId() == 1);
        assertThat(allFollowersForUser.get(0).getId() == 2);
    }


    @Test
    public void when_getAllFollowed_then_returnOneFollow() {
        //given
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user1.setUserId(2L);

        Follow follow = Follow.builder()
                .id(1L)
                .following(user1)
                .followed(user2)
                .build();
        //when
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user2));
        when(followRepository.findAllByFollowing_UserId(1L)).thenReturn(Collections.singletonList(follow));
        List<FollowDto> allFollowed = followService.getAllFollowed(1L);
        //then
        assertThat(allFollowed).isNotEmpty();
        assertThat(allFollowed).isNotNull();
        assertThat(allFollowed.get(0) != null);
        assertThat(allFollowed.get(0).getId() == 1);
        assertThat(allFollowed.get(0).getId() == 2);
        assertThat(allFollowed.get(0).getId() == 1);
    }

    @Test(expected = UserNotFoundException.class)
    public void when_getAllFollowers_thenThrowException() {
        //given
        //when
        when(followService.getAllFollowers(1L)).thenReturn(new ArrayList<>());
        //then
    }

    @Test(expected = UserNotFoundException.class)
    public void when_getAllFollowed_thenThrowException() {
        //given
        //when
        when(followService.getAllFollowed(1L)).thenReturn(new ArrayList<>());
        //then
    }

    @Test
    public void when_follow_then_returnOneFollow() {
        //given
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);

        FollowDto followDto = FollowDto.builder()
                .id(1L)
                .following(user1)
                .followed(user2)
                .build();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        FollowDto follow = followService.follow(followDto);
        //then
        assertThat(follow.getId() == 1L);
        assertThat(follow.getFollowed().getUserId() == 2L);
        assertThat(follow.getFollowing().getUserId() == 1L);
    }

    @Test(expected = SpringTwitterException.class)
    public void when_follow_then_throwException(){
        //given
        User user = new User();
        user.setUserId(1L);
        FollowDto followDto = FollowDto.builder()
                .id(1L)
                .followed(user)
                .following(user)
                .build();
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        followService.follow(followDto);
        //then
    }


}
