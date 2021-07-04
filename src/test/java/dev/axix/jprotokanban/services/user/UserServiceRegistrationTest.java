package dev.axix.jprotokanban.services.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import dev.axix.jprotokanban.models.role.Role;
import dev.axix.jprotokanban.models.user.User;
import dev.axix.jprotokanban.models.user.UserRepository;

@SpringBootTest
public class UserServiceRegistrationTest {

  @SpyBean
  UserService userService;

  @MockBean
  UserRepository userRepository;

  @MockBean
  PasswordEncoder passwordEncoder;

  @Mock
  User user;

  @Mock
  Role role;

  @Mock
  Logger log;

  @Mock
  User createdUser;

  String login = "login@login.test";
  String password = "password";
  Long createdUserId = 42L;
  String passwordHash = "passwordHash";

  @BeforeEach
  void setUp() throws UserAlreadyExistException {
    doReturn(log).when(userService).getLog();
    doReturn(user).when(userService).getNewUser();
    doReturn(role).when(userService).getNewRole();
    doNothing().when(userService).checkUserAlreadyExist(login);
    when(userRepository.save(user)).thenReturn(createdUser);
    when(createdUser.getId()).thenReturn(createdUserId);
    when(passwordEncoder.encode(password)).thenReturn(passwordHash);
  }

  @Test
  void testRegistration() throws UserAlreadyExistException {
    Boolean result = userService.registration(login, password);

    verify(userService).checkUserAlreadyExist(login);
    verify(userService).getNewUser();
    verify(userService).getNewRole();
    verify(role).setRole(UserService.DEFAULT_ROLE);
    verify(user).setActive(true);
    verify(user).setUserName(login);
    verify(passwordEncoder).encode(password);
    verify(user).setPassword(passwordHash);
    verify(userRepository).save(user);
    verify(createdUser, times(2)).getId();

    verify(log).info("Successfuly created user: " + createdUserId);

    assertTrue(result);
  }

  @Test
  void testRegistrationFailed() throws UserAlreadyExistException {
    when(createdUser.getId()).thenReturn(null);

    Boolean result = userService.registration(login, password);

    verify(userService).checkUserAlreadyExist(login);
    verify(userService).getNewUser();
    verify(userService).getNewRole();
    verify(role).setRole(UserService.DEFAULT_ROLE);
    verify(user).setActive(true);
    verify(user).setUserName(login);
    verify(passwordEncoder).encode(password);
    verify(user).setPassword(passwordHash);
    verify(userRepository).save(user);
    verify(createdUser, times(1)).getId();

    verify(log).warn("Failed attempt to create user: " + login);

    assertFalse(result);
  }
}
