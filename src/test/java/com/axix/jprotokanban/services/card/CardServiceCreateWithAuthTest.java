package com.axix.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.axix.jprotokanban.models.card.Card;
import com.axix.jprotokanban.models.card.CardRepository;
import com.axix.jprotokanban.models.column.Column;
import com.axix.jprotokanban.models.user.User;
import com.axix.jprotokanban.services.card.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;

@SpringBootTest
public class CardServiceCreateWithAuthTest {

  @MockBean
  CardRepository cardRepository;

  @Mock
  User user;

  @Mock
  Column column;

  @Mock
  Authentication authentication;

  String title = "title";
  String text = "text";
  Long columnId = 42L;

  @Mock
  Card card;

  @SpyBean
  CardService cardService;

  @BeforeEach
  void setUp() {
    doReturn(user).when(cardService).getUserFromAuth(authentication);
    doReturn(column).when(cardService).getColumnFromParams(columnId);
    doReturn(card).when(cardService).getNewCard();
    when(cardRepository.save(card)).thenReturn(card);
  }

  @Test
  void testCreateWithAuth() {
    Card cardResult = cardService.createWithAuth(title, text, columnId, authentication);

    verify(cardService).getUserFromAuth(authentication);
    verify(cardService).getColumnFromParams(columnId);
    verify(cardService).getNewCard();

    verify(card).setTitle(title);
    verify(card).setText(text);

    verify(column).addCard(card);
    verify(user).addCard(card);

    verify(cardRepository).save(card);

    assertNotNull(cardResult);
  }
}
