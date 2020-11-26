package socialmediaapp.twitterinspiredapp.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.FollowDto;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.exceptions.UserNotFoundException;
import socialmediaapp.twitterinspiredapp.model.Follow;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.FollowRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        User following = userRepository.findById(followDto.getFollowing().getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        User followed = userRepository.findById(followDto.getFollowed().getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (followed.equals(following)) {
            throw new SpringTwitterException("You cannot follow yourself!");
        }

        Follow follow = fromFollowDto(followDto);
        followRepository.save(follow);

        return followDto;
    }

    public List<FollowDto> getAllFollowers(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        return followRepository.findAllByFollowed_UserId(userId)
                .stream()
                .map(this::toFollowDto)
                .collect(Collectors.toList());
    }

    public List<FollowDto> getAllFollowed(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        return followRepository.findAllByFollowing_UserId(userId)
                .stream()
                .map(this::toFollowDto)
                .collect(Collectors.toList());
    }


    private FollowDto toFollowDto(Follow follow) {
        return FollowDto.builder()
                .id(follow.getId())
                .following(follow.getFollowing())
                .followed(follow.getFollowed())
                .build();
    }

    private Follow fromFollowDto(FollowDto followDto) {
        User following = userRepository.findById(followDto.getFollowing().getUserId())
                .orElseThrow(() -> new SpringTwitterException("User not found!"));

        User followed = userRepository.findById(followDto.getFollowed().getUserId())
                .orElseThrow(() -> new SpringTwitterException("User not found!"));

        return Follow.builder()
                .followDate(Timestamp.valueOf(LocalDateTime.now()))
                .following(following)
                .followed(followed)
                .id(followDto.getId())
                .build();
    }
}
