package com.example.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.example.jprotokanban.exceptions.custom.CodeExceptionManager;
import com.example.jprotokanban.models.user.MyUserDetails;
import com.example.jprotokanban.models.user.User;
import com.example.jprotokanban.models.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;


@SpringBootTest
public class CardServiceGetUserFromAuthTest {
  @SpyBean
  CardService cardService;

  @MockBean
  UserRepository userRepository;

  @Mock
  User user;

  @Mock
  Authentication authentication;

  @Mock
  MyUserDetails userDetails;

  Long userId = 42L;

  @BeforeEach
  void setUp() {
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userDetails.getId()).thenReturn(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
  }

  @Test
  void testGetUserFromAuth() {
    User userResult = cardService.getUserFromAuth(authentication);

    assertNotNull(userResult);

    verify(authentication).getPrincipal();
    verify(userDetails).getId();
    verify(userRepository).findById(userId);
  }

  @Test
  void testGetUserFromAuthThrowNotFound() {
    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    Exception e = assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
        () -> cardService.getUserFromAuth(authentication));

    assertEquals("Entity not found (user 42 not found!)", e.getMessage());
  }
}
