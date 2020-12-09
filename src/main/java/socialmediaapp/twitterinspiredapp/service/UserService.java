package socialmediaapp.twitterinspiredapp.service;

import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.dto.SignUpResponse;
import socialmediaapp.twitterinspiredapp.model.User;

import java.util.Optional;

public interface UserService {
    User getCurrentUser();

    SignUpResponse signup(RegisterRequest registerRequest);

    boolean verifyAccount(String token);


    Optional<User> getUserById(Long userId);
}
