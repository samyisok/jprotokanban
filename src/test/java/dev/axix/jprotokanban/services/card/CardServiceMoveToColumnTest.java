package dev.axix.jprotokanban.services.card;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import dev.axix.jprotokanban.models.card.Card;
import dev.axix.jprotokanban.models.card.CardRepository;
import dev.axix.jprotokanban.models.column.Column;


@SpringBootTest
public class CardServiceMoveToColumnTest {
  @SpyBean
  CardService cardService;

  @MockBean
  CardRepository cardRepository;

  @Mock
  Card card;

  Long cardId = 42L;

  @Mock
  Column columnOld;

  @Mock
  Column columnNew;

  Long columnIdNew = 32L;
  Long columnIdOld = 31L;

  @BeforeEach
  void setUp() {
    doReturn(card).when(cardService).getCard(cardId);
    when(card.getColumn()).thenReturn(columnOld);
    doReturn(columnNew).when(cardService).getColumnFromParams(columnIdNew);

    when(columnNew.getId()).thenReturn(columnIdNew);
    when(columnOld.getId()).thenReturn(columnIdOld);
  }


  @Test
  void testMoveToColumn() {
    Boolean result = cardService.moveToColumn(cardId, columnIdNew);
    assertTrue(result);

    verify(cardService).getCard(cardId);
    verify(cardService).getColumnFromParams(columnIdNew);
    verify(card).getColumn();
    verify(columnNew).getId();
    verify(columnOld).getId();
    verify(cardRepository).save(card);
  }

  @Test
  void testMoveToColumnFalse() {
    when(columnNew.getId()).thenReturn(columnIdOld);
    Boolean result = cardService.moveToColumn(cardId, columnIdNew);
    assertFalse(result);
  }
}
