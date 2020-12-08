package socialmediaapp.twitterinspiredapp.service;

import socialmediaapp.twitterinspiredapp.dto.AuthenticationResponse;
import socialmediaapp.twitterinspiredapp.dto.RefreshTokenRequest;
import socialmediaapp.twitterinspiredapp.model.LoginRequest;

public interface AuthenticationService {

    AuthenticationResponse login(LoginRequest loginRequest);

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
