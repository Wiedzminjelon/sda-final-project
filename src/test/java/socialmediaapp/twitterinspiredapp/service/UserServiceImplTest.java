package socialmediaapp.twitterinspiredapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;
import socialmediaapp.twitterinspiredapp.exceptions.EmailExistsException;
import socialmediaapp.twitterinspiredapp.exceptions.PasswordNotMatchException;
import socialmediaapp.twitterinspiredapp.exceptions.UsernameExistsException;
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
    public void when_signup_thenSaveUser() {
        //given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("pass");
        registerRequest.setConfirmedPassword("pass");
        registerRequest.setEmail("email@email.com");

        User user = userService.createUser(registerRequest);

        //when
        when(userRepository.save(any())).thenReturn(user);
        User userSaved = userService.signup(registerRequest);

        //then
        assertThat(userSaved).isNotNull();
        assertFalse(userSaved.isEnabled());
        assertThat(userSaved.getUsername().equals("username"));
        assertThat(userSaved.getEmail().equals("email@email.com"));

    }

    @Test(expected = PasswordNotMatchException.class)
    public void when_signup_thenThrowPasswordNotMatchException() {
        //given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("pass");
        registerRequest.setConfirmedPassword("pass2");
        registerRequest.setEmail("email@email.com");

        //when
        User userSaved = userService.signup(registerRequest);
    }

    @Test(expected = UsernameExistsException.class)
    public void when_signup_thenThrowUsernameExistsException() {
        //given
        User user = new User();
        user.setUsername("username");

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("pass");
        registerRequest.setConfirmedPassword("pass");
        registerRequest.setEmail("email@email.com");

        //when
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        User userSaved = userService.signup(registerRequest);
    }

    @Test(expected = EmailExistsException.class)
    public void when_signup_thenThrowEmailExistsException() {
        //given
        User user = new User();
        user.setEmail("email@email.com");

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("pass");
        registerRequest.setConfirmedPassword("pass2");
        registerRequest.setEmail("email@email.com");

        //when
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        User userSaved = userService.signup(registerRequest);
    }


    @Test
    public void when_verifyAccount_thenReturnEnabledUser() {
        //given
        User user = User.builder()
                .username("username")
                .build();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken("aaa-bbb");

        //when
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(verificationTokenRepository.findByToken("aaa-bbb")).thenReturn(Optional.of(verificationToken));
        boolean isVerified = userService.verifyAccount(verificationToken.getToken());

        //then
        assertTrue(isVerified);
    }

    @Test
    public void when_verifyAccount_thenReturnDisabledUser() {
        //given
        User user = new User();

        //when
        boolean isVerified = userService.verifyAccount(String.valueOf(new VerificationToken()));

        //then
        assertFalse(isVerified);
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
