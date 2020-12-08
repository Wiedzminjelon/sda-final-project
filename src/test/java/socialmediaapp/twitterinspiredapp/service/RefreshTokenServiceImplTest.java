package socialmediaapp.twitterinspiredapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import socialmediaapp.twitterinspiredapp.model.RefreshToken;
import socialmediaapp.twitterinspiredapp.repository.RefreshTokenRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RefreshTokenServiceImplTest {

    RefreshTokenServiceImpl refreshTokenServiceImpl;

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Before
    public void init(){
        refreshTokenServiceImpl = new RefreshTokenServiceImpl(refreshTokenRepository);
    }

    @Test
    public void when_generateRefreshToken_thenReturnRefreshToken() {
        //given
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(1L);
        refreshToken.setToken(UUID.randomUUID().toString());
        //when
       when(refreshTokenRepository.save(any())).thenReturn(refreshToken);
        RefreshToken refreshToken2 = refreshTokenServiceImpl.generateRefreshToken();
        //then
        assertThat(refreshToken2.equals(refreshToken));
        assertThat(refreshToken2).isNotNull();
    }

    @Test
    public void when_validateRefreshToken_then_returnRefreshToken() {

    }

    @Test
    public void deleterefreshToken() {
    }
}
