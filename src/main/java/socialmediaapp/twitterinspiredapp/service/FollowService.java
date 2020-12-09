package socialmediaapp.twitterinspiredapp.service;

import socialmediaapp.twitterinspiredapp.dto.FollowDto;

import java.util.List;

public interface FollowService {
    FollowDto follow(FollowDto followDto);

    List<FollowDto> getAllFollowers(Long userId);

    List<FollowDto> getAllFollowed(Long userId);
}
