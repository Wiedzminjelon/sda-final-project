package socialmediaapp.twitterinspiredapp.service;

import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.PostDto;
import socialmediaapp.twitterinspiredapp.model.Post;
import socialmediaapp.twitterinspiredapp.model.Repost;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.repository.PostRepository;
import socialmediaapp.twitterinspiredapp.repository.RepostRepository;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;

import java.util.Optional;

@Service
public class RepostServiceImpl implements RepostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RepostRepository repostRepository;

    public RepostServiceImpl(UserRepository userRepository, PostRepository postRepository, RepostRepository repostRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.repostRepository = repostRepository;
    }

    @Override
    public RepostDto repost(RepostDto repostDto) {
        User user = userRepository.findById(repostDto.getUser().getUserId()).get();
        Post postById = postRepository.findById(repostDto.getPost().getId()).get();

        Repost toSave = fromRepostDto(repostDto);

        Repost save = repostRepository.save(toSave);

        return new RepostDto();
    }

    private Repost fromRepostDto(RepostDto repostDto) {
        Repost repost = new Repost();
        repost.setUser(repostDto.getUser());
        repost.setPost(repostDto.getPost());
        return repost;
    }
}
