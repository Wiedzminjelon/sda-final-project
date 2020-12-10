package socialmediaapp.twitterinspiredapp.service;

import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.model.User;

import java.util.Optional;

public interface UserService {
    User getCurrentUser();

    User signup(RegisterRequest registerRequest);

    boolean verifyAccount(String token);

    Optional<User> getUserById(Long userId);

    User createUser(RegisterRequest registerRequest);
}
