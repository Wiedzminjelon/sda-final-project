package socialmediaapp.twitterinspiredapp.service;

import socialmediaapp.twitterinspiredapp.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken();

    RefreshToken validateRefreshToken(String token);

    void deleteRefreshToken(String token);
}
