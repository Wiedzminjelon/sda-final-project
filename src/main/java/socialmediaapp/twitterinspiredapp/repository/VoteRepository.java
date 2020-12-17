package socialmediaapp.twitterinspiredapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.model.Vote;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndAndUserOrderByVoteIdDesc(Post post, User user);
}
