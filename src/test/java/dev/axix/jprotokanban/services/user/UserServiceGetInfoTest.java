package dev.axix.jprotokanban.services.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;
import dev.axix.jprotokanban.models.user.MyUserDetails;
import dev.axix.jprotokanban.models.user.User;
import dev.axix.jprotokanban.models.user.UserRepository;

@SpringBootTest
public class UserServiceGetInfoTest {

  @SpyBean
  UserService userService;

  @MockBean
  UserRepository userRepository;

  @Mock
  Authentication authentication;

  @Mock
  MyUserDetails myUserDetails;

  @Mock
  User user;

  Long userId = 42L;

  @BeforeEach
  void setUp() {
    when(authentication.getPrincipal()).thenReturn(myUserDetails);
    when(myUserDetails.getId()).thenReturn(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
  }

  @Test
  void testGetInfo() {
    User userResult = userService.getUserInfo(authentication);
    assertNotNull(userResult);
    assertSame(user, userResult);

    verify(authentication).getPrincipal();
    verify(myUserDetails).getId();
    verify(userRepository).findById(userId);
  }
}
