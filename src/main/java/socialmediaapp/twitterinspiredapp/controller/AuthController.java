package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.AuthenticationResponse;
import socialmediaapp.twitterinspiredapp.dto.RefreshTokenRequest;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.dto.SignUpResponse;
import socialmediaapp.twitterinspiredapp.model.LoginRequest;
import socialmediaapp.twitterinspiredapp.service.AuthService;
import socialmediaapp.twitterinspiredapp.service.RefreshTokenService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")

public class AuthController {

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody @Valid RegisterRequest registerRequest) throws MessagingException {
        authService.signup(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activation successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleterefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("refresh Token deleted successfully!");
    }
}
