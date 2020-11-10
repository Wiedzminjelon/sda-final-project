package socialmediaapp.twitterinspiredapp.service;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import socialmediaapp.twitterinspiredapp.dto.VoteDto;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.model.Vote;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;
import socialmediaapp.twitterinspiredapp.repository.VoteRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static socialmediaapp.twitterinspiredapp.enums.VOTE_TYPE.UPVOTE;

@Service
@Data
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public VoteService(VoteRepository voteRepository, PostRepository postRepository, UserRepository userRepository, AuthService authService) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public VoteDto vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Post with this ID not found"));
        User user = authService.getUserById(voteDto.getUserId()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "User with this ID not found"));

        Optional<Vote> vote = voteRepository
                .findTopByPostAndAndUserOrderByVoteIdDesc(post, user);

        if (vote.isPresent() && vote.get().getVote_type().equals(voteDto.getVote_type())) {
            throw new SpringTwitterException("You have already" + voteDto.getVote_type() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVote_type())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapVoteDtoToVote(voteDto));
        postRepository.save(post);

        return voteDto;
    }

    private Vote mapVoteDtoToVote(VoteDto voteDto) {
        return Vote.builder()
                .vote_type(voteDto.getVote_type())
                .post(postRepository.findById(voteDto.getPostId()).
                        orElseThrow(()-> new SpringTwitterException("Post not found!")))
                .user(userRepository.findById(voteDto.getUserId()).orElseThrow(()->new SpringTwitterException("User not found!")))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

}
