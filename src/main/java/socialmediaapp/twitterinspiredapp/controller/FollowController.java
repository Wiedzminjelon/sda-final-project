package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.FollowDto;
import socialmediaapp.twitterinspiredapp.service.FollowService;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/")
    public ResponseEntity<FollowDto> follow(@RequestBody FollowDto followDto) {
        return new ResponseEntity<>(followService.follow(followDto), HttpStatus.OK);
    }

    @GetMapping("/all-for-user/{userId}")
    public ResponseEntity<List<FollowDto>> getAllFollowers(@PathVariable long userId) {
        return new ResponseEntity<>(followService.getAllFollowers(userId), HttpStatus.OK);
    }

    @GetMapping("/all-by-user/{userId}")
    public ResponseEntity<List<FollowDto>> getAllFollowed(@PathVariable long userId) {
        return new ResponseEntity<>(followService.getAllFollowed(userId), HttpStatus.OK);
    }

}
