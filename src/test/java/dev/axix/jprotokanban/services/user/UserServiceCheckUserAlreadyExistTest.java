package dev.axix.jprotokanban.services.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.models.user.User;
import dev.axix.jprotokanban.models.user.UserRepository;

@SpringBootTest
public class UserServiceCheckUserAlreadyExistTest {

  @SpyBean
  UserService userService;

  @MockBean
  UserRepository userRepository;

  String login = "login@login.test";

  @Mock
  User user;

  @BeforeEach
  void setUp() {
    when(userRepository.findByUserName(login)).thenReturn(Optional.empty());
  }

  @Test
  void testCheckUserAlreadyExist() {
    assertDoesNotThrow(() -> userService.checkUserAlreadyExist(login));
  }

  @Test
  void testCheckUserAlreadyExistIsTrue() {
    when(userRepository.findByUserName(login)).thenReturn(Optional.of(user));
    Exception e = assertThrows(UserAlreadyExistException.class,
        () -> userService.checkUserAlreadyExist(login));
    assertEquals("User Already Exists", e.getMessage());
  }

}
