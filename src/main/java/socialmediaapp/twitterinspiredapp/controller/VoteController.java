package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialmediaapp.twitterinspiredapp.dto.VoteDto;
import socialmediaapp.twitterinspiredapp.service.VoteService;
import socialmediaapp.twitterinspiredapp.service.VoteServiceImpl;

@RestController
@RequestMapping("api/votes/")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<VoteDto> vote(@RequestBody VoteDto voteDto){
        return new ResponseEntity<>(voteService.vote(voteDto),HttpStatus.CREATED);
    }

}
