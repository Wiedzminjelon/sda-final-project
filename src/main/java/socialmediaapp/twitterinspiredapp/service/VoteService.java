package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import socialmediaapp.twitterinspiredapp.dto.VoteDto;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.Vote;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;
import socialmediaapp.twitterinspiredapp.repository.VoteRepository;

import java.util.Optional;

import static socialmediaapp.twitterinspiredapp.enums.VOTE_TYPE.UPVOTE;

@Service
@AllArgsConstructor
@Data
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Post with this ID not found"));
        Optional<Vote> voteByPostAndUser = voteRepository.
                findTopByPostAndAndUserOrderByVoteIdDesc(post,authService.getUserById(post.getUser().getUserId()));

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVote_type().equals(voteDto.getVote_type())){
            throw new SpringTwitterException("You have already" + voteDto.getVote_type() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVote_type())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else{
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post) {

    return Vote.builder()
            .vote_type(voteDto.getVote_type())
            .post(post)
            .user(userRepository.findByUsername(post.getUser().getUsername()))
            .build();
    }

}
