package socialmediaapp.twitterinspiredapp.service;

import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.AuthenticationResponse;
import socialmediaapp.twitterinspiredapp.dto.RefreshTokenRequest;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.dto.SignUpResponse;
import socialmediaapp.twitterinspiredapp.enums.ACCOUNT_TYPE;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.model.LoginRequest;
import socialmediaapp.twitterinspiredapp.model.NotificationEmail;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.model.VerificationToken;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;
import socialmediaapp.twitterinspiredapp.repository.VerificationTokenRepository;
import socialmediaapp.twitterinspiredapp.security.JwtProvider;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;


    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository,
                       MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new SpringTwitterException("User name not found - " + principal.getUsername()));
    }

    @Transactional
    public SignUpResponse signup(RegisterRequest registerRequest) throws MessagingException {
        userAndEmailValidator(registerRequest);

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAccountType(ACCOUNT_TYPE.PRIVATE);
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);

        mailService.sendEmail(new NotificationEmail("Please activate your account", "" + user.getEmail(),
                "Please click this link to activate your account:" + "\n" + "\n" +
                        "http:localhost:8080/auth/accountVerification/" + token + "\n" + "\n"), true);
        return new SignUpResponse("User registered successfully!");
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken
                .orElseThrow(() -> new SpringTwitterException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private void userAndEmailValidator(RegisterRequest registerRequest) {
        if (emailExist(registerRequest.getEmail()) || userExist(registerRequest.getUsername())) {
            throw new SpringTwitterException("Username or email already exist!");
        }
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean userExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringTwitterException("User with " + username + "not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }


    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}

