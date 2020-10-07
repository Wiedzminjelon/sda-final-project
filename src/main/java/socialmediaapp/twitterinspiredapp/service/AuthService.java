package socialmediaapp.twitterinspiredapp.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.enums.ACCOUNT_TYPE;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.model.VerificationToken;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;
import socialmediaapp.twitterinspiredapp.repository.VerificationTokenRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        userAndEmailValidator(registerRequest);

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setAccount_type(ACCOUNT_TYPE.PRIVATE);
        user.setCreated(Instant.now());
        user.setEnabled(true);

        userRepository.save(user);
        generateVerificationToken(user);
    }


    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private void userAndEmailValidator(RegisterRequest registerRequest){
        if (emailExist(registerRequest.getEmail()) || userExist(registerRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"User with this username or email exists!");
        }
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean userExist(String username){
        return userRepository.findByUsername(username).isPresent();
    }
}
