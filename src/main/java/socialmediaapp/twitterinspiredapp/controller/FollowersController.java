package socialmediaapp.twitterinspiredapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.FollowersDto;
import socialmediaapp.twitterinspiredapp.service.FollowersService;

import java.util.List;

@RestController()
@AllArgsConstructor
public class FollowersController {
    private final FollowersService followersService;


    @PostMapping("/follow")
    public ResponseEntity<FollowersDto> follow(@RequestBody FollowersDto followersDto) {
        return new ResponseEntity<>(followersService.follow(followersDto), HttpStatus.OK);
    }

    @GetMapping("/all-followers-for-user/{username}")
    public ResponseEntity<List<FollowersDto>> getAllFollowersForUser (@PathVariable String username){
        return new ResponseEntity<>(followersService.getAllFollowersForUser(username), HttpStatus.OK);
    }

}
