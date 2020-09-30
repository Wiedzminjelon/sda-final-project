package socialmediaapp.twitterinspiredapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.service.AuthService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid RegisterRequest registerRequest) {
            authService.signup(registerRequest);
            return new ResponseEntity<>("User registration successful", HttpStatus.CREATED);
    }
}
