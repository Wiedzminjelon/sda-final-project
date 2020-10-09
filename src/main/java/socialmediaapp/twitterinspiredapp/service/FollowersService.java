package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.FollowersDto;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.Followers;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.FollowersRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Data
public class FollowersService {
    private final FollowersRepository followersRepository;
    private final UserRepository userRepository;

    public FollowersDto follow(FollowersDto followersDto) {
        Followers followers = mapFollowersDtoToFollowers(followersDto);
        followersRepository.save(followers);
        return followersDto;
    }

    public List<FollowersDto> getAllFollowersForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return followersRepository.findAllByFollowed_UserId(user.getUserId())
                .stream()
                .map(this::mapFollowersToFollowersDto)
                .collect(Collectors.toList());
    }


//    public List<Followers> showAllFollowedByUser (String username){
//
//    }


    private FollowersDto mapFollowersToFollowersDto(Followers followers) {
        return FollowersDto.builder()
                .followedUserName(followers.getFollowed().getUsername())
                .followingUserName(followers.getFollowing().getUsername())
                .build();
    }

    private Followers mapFollowersDtoToFollowers(FollowersDto followersDto) {
        User following = userRepository.findByUsername(followersDto.getFollowingUserName())
                .orElseThrow(()-> new SpringTwitterException("User not found!"));

        User followed = userRepository.findByUsername(followersDto.getFollowedUserName())
                .orElseThrow(()-> new SpringTwitterException("User not found!"));

        return Followers.builder()
                .followDate(Instant.now())
                .following(following)
                .followed(followed)
                .build();
    }
}
