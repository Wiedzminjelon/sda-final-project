package socialmediaapp.twitterinspiredapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.AuthenticationResponse;
import socialmediaapp.twitterinspiredapp.dto.RefreshTokenRequest;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.dto.SignUpResponse;
import socialmediaapp.twitterinspiredapp.model.LoginRequest;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.service.AuthenticationService;
import socialmediaapp.twitterinspiredapp.service.UserServiceImpl;
import socialmediaapp.twitterinspiredapp.service.RefreshTokenService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;

    public AuthController(UserServiceImpl userServiceImpl, RefreshTokenService refreshTokenService, AuthenticationService authenticationService) {
        this.userServiceImpl = userServiceImpl;
        this.refreshTokenService = refreshTokenService;
        this.authenticationService = authenticationService;
    }



    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody @Valid RegisterRequest registerRequest) throws MessagingException {
        userServiceImpl.signup(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        userServiceImpl.verifyAccount(token);
        return new ResponseEntity<>("Account activation successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authenticationService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleterefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("refresh Token deleted successfully!");
    }

    @GetMapping("/user")
    public User getUser(){
        return userServiceImpl.getCurrentUser();
    }
}
