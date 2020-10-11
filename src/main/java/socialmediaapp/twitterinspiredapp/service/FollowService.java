package socialmediaapp.twitterinspiredapp.service;

import lombok.Data;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.FollowDto;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.Follow;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.FollowRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FollowDto follow(FollowDto followDto) {
        User followingUser = userRepository.findByUsername(followDto.getFollowingUserName())
                .orElseThrow(()-> new SpringTwitterException("User not found!"));
        User followedUser = userRepository.findByUsername(followDto.getFollowedUserName())
                .orElseThrow(()-> new SpringTwitterException("User not found!"));

        if (followedUser.equals(followingUser)){
            throw new SpringTwitterException("You cannot follow yourself!");
        }

        Follow follow = mapFollowDtoToFollow(followDto);
        followRepository.save(follow);

        return followDto;
    }

    public List<FollowDto> getAllFollowersForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return followRepository.findAllByFollowed_UserId(user.getUserId())
                .stream()
                .map(this::mapFollowToFollowDto)
                .collect(Collectors.toList());
    }

    public List<FollowDto> getAllFollowedByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return followRepository.findAllByFollowing_UserId(user.getUserId())
                .stream()
                .map(this::mapFollowToFollowDto)
                .collect(Collectors.toList());
    }


    private FollowDto mapFollowToFollowDto(Follow follow) {
        return FollowDto.builder()
                .followedUserName(follow.getFollowed().getUsername())
                .followingUserName(follow.getFollowing().getUsername())
                .id(follow.getId())
                .build();
    }

    private Follow mapFollowDtoToFollow(FollowDto followDto) {
        User following = userRepository.findByUsername(followDto.getFollowingUserName())
                .orElseThrow(() -> new SpringTwitterException("User not found!"));

        User followed = userRepository.findByUsername(followDto.getFollowedUserName())
                .orElseThrow(() -> new SpringTwitterException("User not found!"));

        return Follow.builder()
                .followDate(Instant.now())
                .following(following)
                .followed(followed)
                .id(followDto.getId())
                .build();
    }
}
