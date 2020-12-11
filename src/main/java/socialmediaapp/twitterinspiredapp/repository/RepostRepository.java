package socialmediaapp.twitterinspiredapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socialmediaapp.twitterinspiredapp.model.Repost;

public interface RepostRepository extends JpaRepository<Repost, Long> {

}
