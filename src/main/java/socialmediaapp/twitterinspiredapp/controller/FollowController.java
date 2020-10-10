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

    @GetMapping("/all-for-user/{username}")
    public ResponseEntity<List<FollowDto>> getAllFollowersForUser(@PathVariable String username) {
        return new ResponseEntity<>(followService.getAllFollowersForUser(username), HttpStatus.OK);
    }

    @GetMapping("/all-by-user/{username}")
    public ResponseEntity<List<FollowDto>> getAllFollowedByUser(@PathVariable String username) {
        return new ResponseEntity<>(followService.getAllFollowedByUser(username), HttpStatus.OK);
    }

}
