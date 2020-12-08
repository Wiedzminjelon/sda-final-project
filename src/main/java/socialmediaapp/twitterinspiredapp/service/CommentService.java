package socialmediaapp.twitterinspiredapp.service;

import socialmediaapp.twitterinspiredapp.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto save(CommentDto commentDto);

    List<CommentDto> getAllCommentsForPost(Long postId);

    List<CommentDto> getAllCommentsForUser(Long userId);

}
