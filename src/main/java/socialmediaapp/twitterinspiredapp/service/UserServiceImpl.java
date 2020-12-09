package socialmediaapp.twitterinspiredapp.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.dto.SignUpResponse;
import socialmediaapp.twitterinspiredapp.enums.ACCOUNT_TYPE;
import socialmediaapp.twitterinspiredapp.exceptions.SpringTwitterException;
import socialmediaapp.twitterinspiredapp.exceptions.UserNotFoundException;
import socialmediaapp.twitterinspiredapp.model.*;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;
import socialmediaapp.twitterinspiredapp.repository.VerificationTokenRepository;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;


    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository,
                           MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Transactional
    public SignUpResponse signup(RegisterRequest registerRequest) {
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

        try {
            mailService.sendEmail(new NotificationEmail("Please activate your account", "" + user.getEmail(),
                    "Please click this link to activate your account:" + "\n" + "\n" +
                            "http:localhost:8080/auth/accountVerification/" + token + "\n" + "\n"), true);
        } catch (MessagingException messagingException) {
            throw new SpringTwitterException("There was a problem with sending an activation email. Please register again or contact support");
        }

        return new SignUpResponse("User registered successfully!");
    }

    public boolean verifyAccount(String token) {
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(token);

        if (optionalVerificationToken.isPresent()) {
            VerificationToken verificationToken = optionalVerificationToken.get();
            fetchUserAndEnable(verificationToken);
            return true;
        }
        return false;
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

    private boolean fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

}

