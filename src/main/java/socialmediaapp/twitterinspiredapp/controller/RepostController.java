package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import socialmediaapp.twitterinspiredapp.service.RepostDto;
import socialmediaapp.twitterinspiredapp.service.RepostService;

@RestController("/api/repost")
public class RepostController {

    private final RepostService repostService;

    public RepostController(RepostService repostService) {
        this.repostService = repostService;
    }

    @PostMapping
    public ResponseEntity<RepostDto> repost(@RequestBody RepostDto repostDto) {
        return new ResponseEntity<>(repostService.repost(repostDto), HttpStatus.OK);

    }
}
