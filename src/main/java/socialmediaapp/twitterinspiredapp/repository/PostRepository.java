package socialmediaapp.twitterinspiredapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialmediaapp.twitterinspiredapp.dto.PostResponse;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser_Username(String username);
}
