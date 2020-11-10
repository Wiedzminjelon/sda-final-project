package socialmediaapp.twitterinspiredapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialmediaapp.twitterinspiredapp.model.Comment;
import socialmediaapp.twitterinspiredapp.model.Post;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> getAllByUser_Username(String username);

    List<Comment> findByPost_Id(Long postId);
}
