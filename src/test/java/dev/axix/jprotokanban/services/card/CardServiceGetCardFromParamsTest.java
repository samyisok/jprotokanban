package dev.axix.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.exceptions.custom.CodeExceptionManager;
import dev.axix.jprotokanban.models.card.Card;
import dev.axix.jprotokanban.models.card.CardRepository;

@SpringBootTest
public class CardServiceGetCardFromParamsTest {

  @SpyBean
  CardService cardService;

  @MockBean
  CardRepository cardRepository;

  @Mock
  Card card;

  Long cardId = 42L;

  @BeforeEach
  void setUp() {
    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
  }

  @Test
  void testGetCardFromParams() {
    Card cardResult = cardService.getCardFromParams(cardId);
    assertNotNull(cardResult);
    assertSame(card, cardResult);
    verify(cardRepository).findById(cardId);
  }


  @Test
  void testGetCardFromParamsThrows() {
    when(cardRepository.findById(cardId)).thenReturn(Optional.empty());
    Exception e = assertThrows(CodeExceptionManager.NOT_FOUND.getExceptionClass(),
        () -> cardService.getCardFromParams(cardId));

    assertEquals("Entity not found (card 42 not found!)", e.getMessage());

  }

}
