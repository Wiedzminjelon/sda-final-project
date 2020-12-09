package socialmediaapp.twitterinspiredapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import socialmediaapp.twitterinspiredapp.model.User;
import socialmediaapp.twitterinspiredapp.model.VerificationToken;
import socialmediaapp.twitterinspiredapp.repository.UserRepository;
import socialmediaapp.twitterinspiredapp.repository.VerificationTokenRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Mock
    private MailService mailService;

    @Before
    public void init() {
        userService = new UserServiceImpl(passwordEncoder, userRepository, verificationTokenRepository, mailService);
    }

    @Test
    public void when_getCurrentUser_thenReturnUser() {
    }

    @Test
    public void when_signup_thenReturnUser() {
    }

    @Test
    public void when_verifyAccount_thenReturnEnabledUser() {
        //given
        VerificationToken token = new VerificationToken();
        //when
        //then
    }

    @Test
    public void when_getUserById_thenReturnUser() {
        //given
        //when
        when(userRepository.findById(any())).thenReturn(java.util.Optional.of(new User()));
        Optional<User> userById = userService.getUserById(1L);
        //then
        assertThat(userById.isPresent());
        assertThat(userById).isNotEmpty();
        assertThat(!userById.get().isEnabled());
    }
}
