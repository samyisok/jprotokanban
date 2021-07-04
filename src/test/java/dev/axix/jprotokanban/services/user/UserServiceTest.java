package dev.axix.jprotokanban.services.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.models.role.Role;
import dev.axix.jprotokanban.models.user.User;


@SpringBootTest
public class UserServiceTest {

  @SpyBean
  UserService userService;

  @Test
  void testGetLog() {
    Logger log = userService.getLog();
    assertNotNull(log);
  }

  @Test
  void testGetNewRole() {
    Role role = userService.getNewRole();
    assertNotNull(role);
  }

  @Test
  void testGetNewUser() {
    User user = userService.getNewUser();
    assertNotNull(user);
  }
}
